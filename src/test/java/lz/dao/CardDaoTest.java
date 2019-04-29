package lz.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import lz.exception.CardException;
import lz.model.Card;

public class CardDaoTest {
    private static final String SESSION_ID = "sessionId";
    private static final String SESSION_ID2 = "sessionId2";
    CardDao cardDao;
    final Card card1 = new Card("", "123456789", new Date());
    final Card card1Masked = new Card("", "12345xxxx-xxxx-xxxx", new Date());
    final Card cardInvalid = new Card("", "123", new Date());

    @Before
    public void setUp() {

    }

    @Test
    public void testAddCard() {
	cardDao = new CardDao();
	cardDao.addCard(card1, SESSION_ID);
	cardDao.addCard(card1Masked, SESSION_ID);
	assertThat(cardDao.getCards().get(SESSION_ID), CoreMatchers.hasItems(card1, card1Masked));
    }

    @Test(expected = CardException.class)
    public void testAddCardExisting() {
	cardDao = new CardDao(createCards());
	cardDao.addCard(card1, SESSION_ID);
    }

    @Test
    public void testDeleteCardsOfSession() {
	cardDao = new CardDao(createCards());
	assertThat(cardDao.getCards().get(SESSION_ID), CoreMatchers.hasItems(card1));
	cardDao.deleteCards(SESSION_ID);
	assertNull(cardDao.getCards().get(SESSION_ID));
	assertThat(cardDao.getCards().get(SESSION_ID2), CoreMatchers.hasItems(card1Masked));
    }

    @Test
    public void testGetCards() {
	cardDao = new CardDao(createCards());
	assertThat(cardDao.getCards(SESSION_ID), CoreMatchers.hasItems(card1));
    }

    @Test
    public void testGetCardsEmpty() {
	cardDao = new CardDao();
	assertEquals(cardDao.getCards(SESSION_ID).size(), 0);
    }

    private Map<String, List<Card>> createCards() {
	final Map<String, List<Card>> cards = new HashMap<>();
	cards.put(SESSION_ID, Arrays.asList(card1));
	cards.put(SESSION_ID2, Arrays.asList(card1Masked));
	return cards;
    }
}
