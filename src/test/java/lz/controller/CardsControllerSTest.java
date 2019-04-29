package lz.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import lz.model.Card;
import lz.model.CardsResponse;
import lz.model.PostCardResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CardsControllerSTest {

    final Card card1 = new Card("bank", "1234-5678-9012-3456", createDate("Nov-2019"));
    final Card card1Masked = new Card("bank", "1234-xxxx-xxxx-xxxx", createDate("Nov-2019"));

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetCards() {

	restTemplate.postForEntity("/api/cards/", card1, PostCardResponse.class);

	final CardsResponse cardsResponse = restTemplate.getForObject("/api/cards/", CardsResponse.class);
	final Set<Card> cards = cardsResponse.getCards();

	assertThat(cards, CoreMatchers.hasItems(card1Masked));
//	assertThat(body).isEqualTo("Hello World");
    }

    @Test
    public void testPostCardSuccessful() {
	final ResponseEntity<PostCardResponse> body = restTemplate.postForEntity("/api/cards/", card1,
		PostCardResponse.class);

	assertThat(body).isEqualTo("Hello World");
    }

    @Test
    public void testCsvUpload() {
	final ResponseEntity<PostCardResponse> body = restTemplate.postForEntity("/api/cards/upload", card1,
		PostCardResponse.class);

	assertThat(body).isEqualTo("Hello World");
    }

    private Date createDate(String date) {
	try {
	    return new SimpleDateFormat("MMM-yyyy").parse(date);
	} catch (final ParseException e) {
	    throw new RuntimeException("invalid date");
	}
    }
}