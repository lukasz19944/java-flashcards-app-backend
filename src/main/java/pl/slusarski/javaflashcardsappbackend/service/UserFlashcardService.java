package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slusarski.javaflashcardsappbackend.domain.Difficulty;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.User;
import pl.slusarski.javaflashcardsappbackend.domain.UserFlashcard;
import pl.slusarski.javaflashcardsappbackend.repository.UserFlashcardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserFlashcardService {

    private final UserFlashcardRepository userFlashcardRepository;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private FlashcardService flashcardService;

    public UserFlashcardService(UserFlashcardRepository userFlashcardRepository, CustomUserDetailsService userDetailsService) {
        this.userFlashcardRepository = userFlashcardRepository;
        this.userDetailsService = userDetailsService;
    }

    public Iterable<UserFlashcard> findAllFlashcardsByCategoryAndKnowledgeLevel(String category, String username) {
        User user = userDetailsService.loadUserByUsername(username);

        return userFlashcardRepository.findAllByFlashcard_CategoryAndKnowledgeLevelInAndUser(category, new int[]{0, 1}, user);
    }

    public Iterable<UserFlashcard> findAllFlashcardsByCategoryAndKnowledgeLevelAndDifficulty(String category, String difficulty, String username) {
        Difficulty[] difficulties;

        if (difficulty.equals("all")) {
            difficulties = Difficulty.values();
        } else {
            difficulties = new Difficulty[]{Difficulty.valueOf(difficulty.toUpperCase())};
        }

        User user = userDetailsService.loadUserByUsername(username);

        return userFlashcardRepository.findAllByFlashcard_CategoryAndKnowledgeLevelInAndFlashcard_DifficultyInAndUser(category, new int[]{0, 1}, difficulties, user);
    }

    public long countAllFlashcardsByKnowledge(String username) {
        User user = userDetailsService.loadUserByUsername(username);

        return userFlashcardRepository.countByKnowledgeLevelAndUser(2, user);
    }

    public Map<String, Integer> countAllFlashcardsByCategoryAndKnowledgeLevel(String username) {
        User user = userDetailsService.loadUserByUsername(username);

        List<Object[]> results = userFlashcardRepository.countByFlashcardCategoryAndKnowledgeLevelAndUser(user.getId());
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Object[] result : results) {
            String category = (String) result[0];
            Integer count = ((Number) result[1]).intValue();

            categoryCountMap.put(category, count);
        }

        Iterable<String> allCategories = flashcardService.findAllCategories();

        for (String category : allCategories) {
            if (!categoryCountMap.containsKey(category)) {
                categoryCountMap.put(category, 0);
            }
        }

        return categoryCountMap;
    }

    public void resetProgress(String username) {
        User user = userDetailsService.loadUserByUsername(username);

        userFlashcardRepository.resetProgress(user.getId());
    }

    public UserFlashcard createOrUpdateKnowledgeLevel(UserFlashcard userFlashcard) {
        return userFlashcardRepository.save(userFlashcard);
    }

    @Transactional
    public void deleteUserFlashcardByFlashcard(Flashcard flashcard) {
        userFlashcardRepository.deleteAllByFlashcard(flashcard);
    }
}
