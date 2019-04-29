package lz.service;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import lz.model.Card;

public class FileProcessorServiceTest {

    private static final byte[] fileBytes = "bank,number,NOV-2019".getBytes();

    @Mock
    private MultipartFile fileMock;

    FileProcessorService service;
    final Card card1 = new Card("", "123456789", new Date());
    final Card card1Masked = new Card("", "12345xxxx-xxxx-xxxx", new Date());
    final Card cardInvalid = new Card("", "123", new Date());

    @Before
    public void setUp() throws IOException {
	MockitoAnnotations.initMocks(this);
	service = new FileProcessorService();
	when(fileMock.getBytes()).thenReturn(fileBytes);
    }

    @Test
    public void testProcessFile() {
	service.processFile(fileMock, c -> {
	}, c -> {
	});
    }

}
