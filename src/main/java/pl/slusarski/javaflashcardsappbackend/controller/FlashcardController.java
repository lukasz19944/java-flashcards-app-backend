package pl.slusarski.javaflashcardsappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.service.ErrorService;
import pl.slusarski.javaflashcardsappbackend.service.FlashcardService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final ErrorService errorService;

    public FlashcardController(FlashcardService flashcardService, ErrorService errorService) {
        this.flashcardService = flashcardService;
        this.errorService = errorService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/accepted")
    public Iterable<Flashcard> getAllAcceptedFlashcards() {
        return flashcardService.findAllAcceptedFlashcards();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/notAccepted")
    public Iterable<Flashcard> getAllNotAcceptedFlashcards() {
        return flashcardService.findAllNotAcceptedFlashcards();
    }


    @GetMapping("/category")
    public Iterable<String> getAllCategories() {
        return flashcardService.findAllCategories();
    }

    @GetMapping("/category/difficulty/{difficulty}")
    public Iterable<String> getAllCategoriesByDifficulty(@PathVariable String difficulty) {
        return flashcardService.findAllCategoriesByDifficulty(difficulty);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createOrUpdateNewFlashcard(@Valid @RequestBody Flashcard flashcard, BindingResult result) {
        ResponseEntity<?> errorMap = errorService.mapValidationService(result);

        if (errorMap != null) {
            return errorMap;
        }

        Flashcard newFlashcard = flashcardService.saveOrUpdateFlashcard(flashcard);

        return new ResponseEntity<>(newFlashcard, HttpStatus.CREATED);
    }

    @GetMapping("/count/")
    public long countAllFlashcards() {
        return flashcardService.countAllFlashcards();
    }

    @GetMapping("/count/category/")
    public Map<String, Integer> countAllFlashcardsByCategory() {
        return flashcardService.countAllFlashcardsByCategory();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{flashcardId}")
    public void deleteFlashcard(@PathVariable String flashcardId) {
        flashcardService.deleteFlashcardById(Long.parseLong(flashcardId));
    }

    @GetMapping("/{flashcardId}")
    public Flashcard getFlashcardById(@PathVariable String flashcardId) {
        return flashcardService.findFlashcardById(Long.parseLong(flashcardId));
    }

    @PostMapping("/propose")
    public ResponseEntity<?> proposeNewFlashcard(@Valid @RequestBody Flashcard flashcard, BindingResult result) {
        ResponseEntity<?> errorMap = errorService.mapValidationService(result);

        if (errorMap != null) {
            return errorMap;
        }

        Flashcard newFlashcard = flashcardService.proposeFlashcard(flashcard);

        return new ResponseEntity<>(newFlashcard, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/accept/{flashcardId}")
    public void acceptFlashcard(@PathVariable String flashcardId) {
        flashcardService.acceptFlashcard(Long.parseLong(flashcardId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reject/{flashcardId}")
    public void rejectFlashcard(@PathVariable String flashcardId) {
        flashcardService.rejectFlashcard(Long.parseLong(flashcardId));
    }
}
