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
    @GetMapping("")
    public Iterable<Flashcard> getAllFlashcards() {
        return flashcardService.findAllFlashcards();
    }

    @GetMapping("/category")
    public Iterable<String> getAllCategories() {
        return flashcardService.findAllCategories();
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

    @GetMapping("/random/")
    public Iterable<Flashcard> createRandomTest() {
        return flashcardService.createRandomTest();
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
}
