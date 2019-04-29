package lz.exception;

import java.io.IOException;

public class CardFormatException extends RuntimeException {
    public CardFormatException(String exception) {
	super(exception);
    }

    public CardFormatException(IOException e) {
	super(e);
    }
}
