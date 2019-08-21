package pl.slusarski.javaflashcardsappbackend.controller;

import org.springframework.web.bind.annotation.*;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.service.FlashcardService;

import java.util.Map;

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

    @PostMapping("")
    public Flashcard createOrUpdateNewFlashcard(@RequestBody Flashcard flashcard) {
        return flashcardService.saveOrUpdateFlashcard(flashcard);
    }

    @GetMapping("/count/")
    public long countAllFlashcards() {
        return flashcardService.countAllFlashcards();
    }

    @GetMapping("/count/level")
    public long countAllFlashcardsByKnowledge() {
        return flashcardService.countAllFlashcardsByKnowledge();
    }

    @GetMapping("/count/category/")
    public Map<String, Integer> countAllFlashcardsByCategory() {
        return flashcardService.countAllFlashcardsByCategory();
    }

    @GetMapping("/count/category-level/")
    public Map<String, Integer> countAllFlashcardsByCategoryAndKnowledge() {
        return flashcardService.countAllFlashcardsByCategoryAndKnowledgeLevel();
    }

    @GetMapping("/reset/")
    public void resetProgress() {
        flashcardService.resetProgress();
    }

    @DeleteMapping("/{flashcardId}")
    public void deleteFlashcard(@PathVariable String flashcardId) {
        flashcardService.deleteFlashcardById(Long.parseLong(flashcardId));
    }

    @GetMapping("/{flashcardId}")
    public Flashcard getFlashcardById(@PathVariable String flashcardId) {
        return flashcardService.findFlashcardById(Long.parseLong(flashcardId));
    }
}
