package lz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import lz.exception.CardFormatException;
import lz.model.Card;

public class CardValidatorServiceTest {

    CardValidatorService service;

    final Card card = new Card("bank", "1234-5678-9012-3456", createDate("Nov-2019"));
    final Card cardInvalidBank = new Card("", "1234-5678-9012-3456", createDate("Nov-2019"));
    final Card cardInvalidDate = new Card("", "1234-5678-9012-3456", new Date());
    final Card cardInvalidNumber = new Card("", "1234-5678-9012-345G", createDate("Nov-2019"));

    @Before
    public void setUp() {
	service = new CardValidatorService();
    }

    @Test
    public void testValidCard() {
	service.validateCard(card);
    }

    @Test(expected = CardFormatException.class)
    public void testInvalidBank() {
	service.validateCard(cardInvalidBank);
    }

    @Test(expected = CardFormatException.class)
    public void testInvalidDate() {
	service.validateCard(cardInvalidDate);
    }

    @Test(expected = CardFormatException.class)
    public void testInvalidNumber() {
	service.validateCard(cardInvalidNumber);
    }

    private Date createDate(String date) {
	try {
	    return new SimpleDateFormat("MMM-yyyy").parse(date);
	} catch (final ParseException e) {
	    throw new RuntimeException("invalid date");
	}
    }

}
