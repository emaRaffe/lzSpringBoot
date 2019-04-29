package lz.model;

import java.util.Set;

public class CardsResponse {
    private Set<Card> cards;

    public CardsResponse() {
    }

    public CardsResponse(Set<Card> cards) {
	this.cards = cards;
    }

    public Set<Card> getCards() {
	return cards;
    }

    public void setCards(Set<Card> cards) {
	this.cards = cards;
    }
}