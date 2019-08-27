package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.stereotype.Service;
import pl.slusarski.javaflashcardsappbackend.domain.Difficulty;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.repository.FlashcardRepository;

import java.security.SecureRandom;
import java.util.*;

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
        return flashcardRepository.findAllByCategoryAndKnowledgeLevelIn(category, new int[]{0, 1});
    }

    public Iterable<Flashcard> findAllFlashcardsByCategoryAndKnowledgeLevelAndDifficulty(String category, String difficulty) {
        Difficulty[] difficulties;

        if (difficulty.equals("all")) {
            difficulties = Difficulty.values();
        } else {
            difficulties = new Difficulty[]{Difficulty.valueOf(difficulty.toUpperCase())};
        }

        return flashcardRepository.findAllByCategoryAndKnowledgeLevelInAndDifficultyIn(category, new int[]{0, 1}, difficulties);
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

    public Flashcard findFlashcardById(Long id) {
        Optional<Flashcard> flashcardOptional = flashcardRepository.findById(id);

        return flashcardOptional.get();
    }

    public void deleteFlashcardById(Long id) {
        flashcardRepository.delete(findFlashcardById(id));
    }

    public Iterable<Flashcard> createRandomTest() {
        List<Flashcard> flashcards = (List<Flashcard>) flashcardRepository.findAll();

        List<Flashcard> randomFlashcards = new ArrayList<>();
        List<Flashcard> copy = new ArrayList<>(flashcards);

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < Math.min(20, flashcards.size()); i++) {
            randomFlashcards.add(copy.remove(rand.nextInt(copy.size())));
        }

        return randomFlashcards;
    }

}
