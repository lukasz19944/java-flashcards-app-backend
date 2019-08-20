package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.stereotype.Service;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.repository.FlashcardRepository;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public Iterable<Flashcard> findAllFlashcards() {
        return flashcardRepository.findAll();
    }

    public Iterable<String> findAllCategories() {
        return flashcardRepository.findAllCategories();
    }

    public Iterable<Flashcard> findAllFlashcardsByCategoryAndKnowledgeLevel(String category) {
        return flashcardRepository.findAllByCategoryAndKnowledgeLevelIn(category, new int[] {0, 1});
    }

    public Flashcard saveOrUpdateFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }
}
