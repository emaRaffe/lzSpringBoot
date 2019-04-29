package lz.service;

import java.util.List;

import lz.dao.CardDao;
import lz.model.Card;

public class CardService {

    private final CardDao cardDao;

    public CardService(CardDao cardDao) {
	this.cardDao = cardDao;
    }

    public CardService() {
	this(new CardDao());
    }

    public void createCard(Card card, String sessionId) {
	cardDao.addCard(card, sessionId);
    }

    public List<Card> getCards(String sessionId) {
	return cardDao.getCards(sessionId);
    }

    public void deleteCards(String sessionId) {
	cardDao.deleteCards(sessionId);
    }
}