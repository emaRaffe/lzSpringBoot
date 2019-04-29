package lz.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceFactory {

    private final CardService cardService;
    private final CardMaskerService cardMaskerService;
    private final CardValidatorService cardValidatorService;
    private final FileProcessorService fileProcessorService;

    public ServiceFactory() {
	cardService = new CardService();
	cardMaskerService = new CardMaskerService();
	cardValidatorService = new CardValidatorService();
	fileProcessorService = new FileProcessorService();
    }

    public CardService createCardService() {
	return cardService;
    }

    public CardMaskerService createCardMaskerService() {
	return cardMaskerService;
    }

    public CardValidatorService createCardValidatorService() {
	return cardValidatorService;
    }

    public FileProcessorService createFileProcessorService() {
	return fileProcessorService;
    }
}
