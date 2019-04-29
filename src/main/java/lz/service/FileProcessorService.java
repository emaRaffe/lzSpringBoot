package lz.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

import lz.dao.RowMapper;
import org.springframework.web.multipart.MultipartFile;

import lz.exception.CardFormatException;
import lz.model.Card;

public class FileProcessorService {

    public void processFile(MultipartFile file, Consumer<Card> rowValidator, Consumer<Card> rowConsumer) {

	String line;
	final CardRowMapper rowMapper = new CardRowMapper();
	try (BufferedReader br = createBufferedReader(file)) {
	    while ((line = br.readLine()) != null) {
		final Card card = rowMapper.mapRow(line);
		rowValidator.accept(card);
		rowConsumer.accept(card);
	    }

	} catch (final IOException e) {
	    throw new CardFormatException(e);
	}

    }

    private BufferedReader createBufferedReader(MultipartFile file) throws IOException {
	return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getBytes())));
    }

    private static class CardRowMapper implements RowMapper<Card> {
	@Override
	public Card mapRow(String record) {
	    final int attributeCount = Card.class.getDeclaredFields().length;
	    final String[] recordAttributes = record.split(",");
	    if (recordAttributes.length != attributeCount) {
		return new Card();
	    } else {
		final DateFormat format = new SimpleDateFormat("MMM-yyyy");
		try {
		    return new Card(recordAttributes[0], recordAttributes[1], format.parse(recordAttributes[2]));
		} catch (final ParseException e) {
		    throw new CardFormatException("invalid date");
		}
	    }
	}
    }

}
