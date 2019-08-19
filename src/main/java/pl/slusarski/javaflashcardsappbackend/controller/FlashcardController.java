package pl.slusarski.javaflashcardsappbackend.controller;

import org.springframework.web.bind.annotation.*;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.service.FlashcardService;

@RestController
@CrossOrigin
@RequestMapping("/api/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping("")
    public Iterable<Flashcard> getAllFlashcards() {
        return flashcardService.findAllFlashcards();
    }

    @GetMapping("/category")
    public Iterable<String> getAllCategories() {
        return flashcardService.findAllCategories();
    }

    @GetMapping("/category/{category}")
    public Iterable<Flashcard> findAllFlashcardsByCategoryAndKnowledgeLevel(@PathVariable String category) {
        return flashcardService.findAllFlashcardsByCategoryAndKnowledgeLevel(category);
    }
}
