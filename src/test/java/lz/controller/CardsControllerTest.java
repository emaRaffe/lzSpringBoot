package lz.controller;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lz.model.Card;
import lz.model.CardsResponse;
import lz.service.CardMaskerService;
import lz.service.CardService;
import lz.service.CardValidatorService;
import lz.service.FileProcessorService;
import lz.service.ServiceFactory;

public class CardsControllerTest {

    private static final String SESSION_ID = "sessionId";

    @InjectMocks
    private CardsController controller;

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpSession sessionMock;

    @Mock
    Errors errorsMock;

    @Mock
    private ServiceFactory serviceFactory;

    @Mock
    private CardService cardServiceMock;

    @Mock
    private CardValidatorService cardValidatorServiceMock;

    @Mock
    private CardMaskerService cardMaskerServiceMock;

    @Mock
    private FileProcessorService fileProcessorServiceMock;

    @Mock
    private RedirectAttributes redirectAttributesMock;

    @Mock
    private MultipartFile fileMock;

    final Card card1 = new Card("", "123456789", createDate("Nov-2019"));
    final Card card1Masked = new Card("", "12345xxxx-xxxx-xxxx", createDate("Nov-2019"));
    final Card cardInvalid = new Card("", "123", new Date());

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	when(requestMock.getSession()).thenReturn(sessionMock);
	when(sessionMock.getId()).thenReturn(SESSION_ID);
	when(serviceFactory.createCardService()).thenReturn(cardServiceMock);
    }

    @Test
    public void testGetCards() {

	when(serviceFactory.createCardMaskerService()).thenReturn(cardMaskerServiceMock);

	when(cardServiceMock.getCards(SESSION_ID)).thenReturn(Arrays.asList(card1));
	when(cardMaskerServiceMock.maskCard(card1)).thenReturn(card1Masked);

	final CardsResponse result = controller.getCards(requestMock);
	assertThat(result.getCards(), CoreMatchers.hasItems(card1Masked));
    }

    @Test
    public void testAddCard() {
	controller.addCard(card1, errorsMock, requestMock);
	verify(cardServiceMock).createCard(card1, SESSION_ID);
    }

    @Test
    public void testUploadCards() {
	when(serviceFactory.createCardValidatorService()).thenReturn(cardValidatorServiceMock);
	when(serviceFactory.createFileProcessorService()).thenReturn(fileProcessorServiceMock);

	controller.uploadCards(fileMock, redirectAttributesMock, requestMock);

	verify(fileProcessorServiceMock).processFile(eq(fileMock), any(), any());
    }

    @Test
    public void testDestroySession() {
	controller.destroySession(requestMock);
	verify(cardServiceMock).deleteCards(SESSION_ID);
    }

    private Date createDate(String date) {
	try {
	    return new SimpleDateFormat("MMM-yyyy").parse(date);
	} catch (final ParseException e) {
	    throw new RuntimeException("invalid date");
	}
    }

}