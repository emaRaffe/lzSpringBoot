package lz.model;

public class PostCardResponse {
    private String message;
    private Card card;

    public PostCardResponse() {
    }

    public PostCardResponse(Card card, String message) {
	this.message = message;
	this.card = card;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public Card getCard() {
	return card;
    }

    public void setCard(Card card) {
	this.card = card;
    }

    @Override
    public String toString() {
        return "PostCardResponse{" +
                "message='" + message + '\'' +
                ", card=" + card +
                '}';
    }
}
