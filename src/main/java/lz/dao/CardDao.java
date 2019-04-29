package lz.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import lz.exception.CardException;
import lz.model.Card;

@Repository
public class CardDao {
    private final static Logger LOG = Logger.getLogger(CardDao.class.getName());

    private final Map<String, List<Card>> cards;

    public CardDao() {
	this(new HashMap<>());
    }

    public CardDao(Map<String, List<Card>> cards) {
	this.cards = cards;
    }

    public List<Card> getCards(String sessionId) {
	final List<Card> cardsBySession = cards.get(sessionId);
	if (cardsBySession != null) {
	    LOG.info(cardsBySession.size() + " cards available for session id: " + sessionId);
	    return cardsBySession;
	} else {
	    LOG.info("no cards available for session id: " + sessionId);
	    return new ArrayList<>();
	}
    }

    public Map<String, List<Card>> getCards() {
	return cards;
    }

    public void addCard(Card card, String sessionId) {
	List<Card> cardsBySessionId = cards.get(sessionId);

	if (cardsBySessionId == null) {
	    LOG.info("creating new card list for session id: " + sessionId);
	    cardsBySessionId = new ArrayList<>();
	    cards.put(sessionId, cardsBySessionId);
	}

	if (cardsBySessionId.contains(card) || isCardNumberDuplicate(cardsBySessionId, card.getCardNumber())) {
	    throw new CardException("card already existing");
	} else {
	    cardsBySessionId.add(card);
	    LOG.info("added new card from " + card.getBankName() + ", current total: " + cardsBySessionId.size());
	}
    }

    private boolean isCardNumberDuplicate(List<Card> cardsBySessionId, String cardNumber) {
	return cardsBySessionId.stream().filter(c -> c.getCardNumber().equals(cardNumber)).count() > 0;
    }

    public void deleteCards(String sessionId) {
	cards.remove(sessionId);
    }
}