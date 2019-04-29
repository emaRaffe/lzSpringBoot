package lz.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import lz.exception.CardFormatException;
import lz.model.Card;

public class CardValidatorService {

    private final Validator validator;

    public CardValidatorService(Validator validator) {
	this.validator = validator;
    }

    public CardValidatorService() {
	this(Validation.buildDefaultValidatorFactory().getValidator());
    }

    public void validateCard(Card card) {
	final Set<ConstraintViolation<Card>> violations = validator.validate(card);
	if (violations.size() > 0) {
	    throw new CardFormatException("invalid card: " + getViolationMessages(violations));
	}
    }

    private String getViolationMessages(Set<ConstraintViolation<Card>> violations) {
	return violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(", "));
    }
}
