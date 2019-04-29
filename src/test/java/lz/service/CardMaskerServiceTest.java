package lz.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import lz.exception.CardFormatException;
import lz.model.Card;

public class CardMaskerServiceTest {

    CardMaskerService service;
    final Card card1 = new Card("", "123456789", new Date());
    final Card card1Masked = new Card("", "12345xxxx-xxxx-xxxx", new Date());
    final Card cardInvalid = new Card("", "123", new Date());

    @Before
    public void setUp() {
	service = new CardMaskerService();
    }

    @Test
    public void testMaskCard() {
	assertEquals(service.maskCard(card1), card1Masked);
    }

    @Test(expected = CardFormatException.class)
    public void testMaskCardInvalid() {
	service.maskCard(cardInvalid);
    }

}
