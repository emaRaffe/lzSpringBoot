package lz.service;

import lz.exception.CardFormatException;
import lz.model.Card;

public class CardMaskerService {

    private static final String MASK = "xxxx-xxxx-xxxx";

    public Card maskCard(Card card) {
	return new Card(card.getBankName(), maskCardNumber(card.getCardNumber()), card.getExpiryDate());
    }

    private String maskCardNumber(String cardNumber) {
	if (cardNumber.length() < 5) {
	    throw new CardFormatException("card number invalid: " + cardNumber);
	}

	return cardNumber.substring(0, 5) + MASK;
    }
}
