package lz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Card implements Comparable<Card> {
    public Card() {
    }

    public Card(String bankName, String cardNumber, Date expiryDate) {
	this.bankName = bankName;
	this.cardNumber = cardNumber;
	this.expiryDate = expiryDate;
    }

    @NotBlank(message = "bankName can't be empty!")
    @NotNull(message = "bankName is mandatory")
    @Size(min = 2, max = 30)
    private String bankName;

    @NotBlank(message = "cardNumber can't be empty!")
    @NotNull(message = "cardNumber is mandatory")
    @Pattern(regexp = "([0-9]{4})-([0-9]{4})-([0-9]{4})-([0-9]{4})")
    private String cardNumber;

    @NotNull(message = "expiryDate is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM-yyyy")
	@DateTimeFormat(pattern = "MMM-yyyy")
    private Date expiryDate;

    public String getBankName() {
	return bankName;
    }

    public void setBankName(String bankName) {
	this.bankName = bankName;
    }

    public String getCardNumber() {
	return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {
	return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
	this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
	return "Card [bankName=" + bankName + ", cardNumber=" + cardNumber + ", expiryDate=" + expiryDate + "]";
    }

    @Override
    public int compareTo(Card card) {
	final int dateCheck = card.getExpiryDate().compareTo(expiryDate);
	final int bankCheck = card.getBankName().compareTo(bankName);
	final int numberCheck = card.getCardNumber().compareTo(cardNumber);
	return dateCheck == 0 ? secondaryChecks(bankCheck, numberCheck) : dateCheck;
    }

    private int secondaryChecks(int bankCheck, int numberCheck) {
	return bankCheck == 0 ? numberCheck : bankCheck;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (bankName == null ? 0 : bankName.hashCode());
	result = prime * result + (cardNumber == null ? 0 : cardNumber.hashCode());
	result = prime * result + (expiryDate == null ? 0 : expiryDate.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Card other = (Card) obj;
	if (bankName == null) {
	    if (other.bankName != null) {
		return false;
	    }
	} else if (!bankName.equals(other.bankName)) {
	    return false;
	}
	if (cardNumber == null) {
	    if (other.cardNumber != null) {
		return false;
	    }
	} else if (!cardNumber.equals(other.cardNumber)) {
	    return false;
	}
	if (expiryDate == null) {
	    if (other.expiryDate != null) {
		return false;
	    }
	} else if (!expiryDate.equals(other.expiryDate)) {
	    return false;
	}
	return true;
    }
}
