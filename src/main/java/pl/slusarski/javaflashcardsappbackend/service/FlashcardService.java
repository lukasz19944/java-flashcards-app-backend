package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.stereotype.Service;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.repository.FlashcardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public long countAllFlashcards() {
        return flashcardRepository.count();
    }

    public long countAllFlashcardsByKnowledge() {
        return flashcardRepository.countByKnowledgeLevel(2);
    }

    public Map<String, Integer> countAllFlashcardsByCategory() {
        List<Object[]> results = flashcardRepository.countByCategory();
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Object[] result : results) {
            String category = (String) result[0];
            Integer count = ((Number) result[1]).intValue();

            categoryCountMap.put(category, count);
        }

        return categoryCountMap;
    }

    public Map<String, Integer> countAllFlashcardsByCategoryAndKnowledgeLevel() {
        List<Object[]> results = flashcardRepository.countByCategoryAndKnowledgeLevel();
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Object[] result : results) {
            String category = (String) result[0];
            Integer count = ((Number) result[1]).intValue();

            categoryCountMap.put(category, count);
        }

        Iterable<String> allCategories = findAllCategories();

        for (String category : allCategories) {
            if (!categoryCountMap.containsKey(category)) {
                categoryCountMap.put(category, 0);
            }
        }

        return categoryCountMap;
    }

    public void resetProgress() {
        flashcardRepository.resetProgress();
    }

}
