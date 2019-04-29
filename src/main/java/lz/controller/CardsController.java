package lz.controller;

import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lz.model.Card;
import lz.model.CardsResponse;
import lz.model.PostCardResponse;
import lz.service.CardMaskerService;
import lz.service.CardService;
import lz.service.CardValidatorService;
import lz.service.FileProcessorService;
import lz.service.ServiceFactory;

@RestController
@RequestMapping(path = "api/cards")
public class CardsController {
    private final static Logger LOG = Logger.getLogger(CardsController.class.getName());

    @Autowired
    private ServiceFactory serviceFactory;

    @GetMapping(path = "/", produces = "application/json")
    public CardsResponse getCards(HttpServletRequest request) {
	final String sessionId = request.getSession().getId();
	final CardService createCardService = serviceFactory.createCardService();
	final List<Card> cards = createCardService.getCards(sessionId);
	return createCardResponse(cards);
    }

    private CardsResponse createCardResponse(final List<Card> cards) {
	final CardMaskerService cardMaskerService = serviceFactory.createCardMaskerService();

	return new CardsResponse(
		cards.stream().map(cardMaskerService::maskCard).collect(Collectors.toCollection(TreeSet<Card>::new)));
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> addCard(@Valid @ModelAttribute Card card, Errors errors, HttpServletRequest request) {
	if (errors.hasErrors()) {
	    return ResponseEntity.badRequest().body(createResult(null, errors));
	}

	final String sessionId = request.getSession().getId();
	final CardService createCardService = serviceFactory.createCardService();
	createCardService.createCard(card, sessionId);

	return ResponseEntity.ok(createResult(card, null));
    }

    private PostCardResponse createResult(Card card, Errors errors) {
	if (card != null) {
	    return new PostCardResponse(card, "success");
	} else if (errors != null) {
	    return new PostCardResponse(null,
		    errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
	}
	return null;
    }

    @PostMapping("/upload")
    public String uploadCards(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
	    HttpServletRequest request) {
	if (file.isEmpty()) {
	    redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	    return "<script>window.location = '/'</script>";
	}

	final String sessionId = request.getSession().getId();
	final CardService cardService = serviceFactory.createCardService();
	final CardValidatorService cardValidatorService = serviceFactory.createCardValidatorService();
	final FileProcessorService fileProcessorService = serviceFactory.createFileProcessorService();

	fileProcessorService.processFile(file, cardValidatorService::validateCard,
		c -> cardService.createCard(c, sessionId));

	redirectAttributes.addFlashAttribute("message",
		"You successfully uploaded '" + file.getOriginalFilename() + "'");

	return "<script>window.location = '/'</script>";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
	final CardService cardService = serviceFactory.createCardService();
	final String sessionId = request.getSession().getId();

	LOG.info("deleting data for session id: " + sessionId);

	cardService.deleteCards(sessionId);
	return "<script>window.location = '/'</script>";
    }
}
