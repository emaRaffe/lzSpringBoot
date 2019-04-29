package lz.service;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lz.dao.CardDao;
import lz.model.Card;

public class CardServiceTest {

    private static final String SESSION_ID = "session id";

    @Mock
    CardDao cardDaoMock;

    CardService service;
    final Card card1 = new Card("", "", createDate("Nov-2019"));
    final Card card2 = new Card("", "", createDate("Dec-2019"));

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	service = new CardService(cardDaoMock);
    }

    @Test
    public void testCreateCard() {

	service.createCard(card1, SESSION_ID);
	verify(cardDaoMock).addCard(card1, SESSION_ID);
    }

    @Test
    public void testDeleteCard() {
	service.deleteCards(SESSION_ID);
	verify(cardDaoMock).deleteCards(SESSION_ID);
    }

    @Test
    public void testGetCards() {
	when(cardDaoMock.getCards(SESSION_ID)).thenReturn(Arrays.asList(card1, card2));
	final List<Card> cards = service.getCards(SESSION_ID);

	assertThat(cards, CoreMatchers.hasItems(card1, card2));
//	assertEquals(cards.get(0), card2);
//	assertEquals(cards.get(1), card1);
    }

    private Date createDate(String date) {
	try {
	    return new SimpleDateFormat("MMM-yyyy").parse(date);
	} catch (final ParseException e) {
	    throw new RuntimeException("invalid date");
	}
    }
}
