package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slusarski.javaflashcardsappbackend.domain.Difficulty;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.User;
import pl.slusarski.javaflashcardsappbackend.domain.UserFlashcard;
import pl.slusarski.javaflashcardsappbackend.repository.UserFlashcardRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
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

        return userFlashcardRepository.findAllByFlashcard_CategoryAndKnowledgeLevelInAndUserAndFlashcard_Accepted(category, new int[]{0, 1}, user, true);
    }

    public Iterable<UserFlashcard> findAllFlashcardsByCategoryAndKnowledgeLevelAndDifficulty(String category, String difficulty, String username) {
        Difficulty[] difficulties;

        if (difficulty.equals("all")) {
            difficulties = Difficulty.values();
        } else {
            difficulties = new Difficulty[]{Difficulty.valueOf(difficulty.toUpperCase())};
        }

        User user = userDetailsService.loadUserByUsername(username);

        return userFlashcardRepository.findAllByFlashcard_CategoryAndKnowledgeLevelInAndFlashcard_DifficultyInAndUserAndFlashcard_Accepted(category, new int[]{0, 1}, difficulties, user, true);
    }

    public long countAllFlashcardsByKnowledge(String username) {
        User user = userDetailsService.loadUserByUsername(username);

        return userFlashcardRepository.countByKnowledgeLevelAndUserAndFlashcard_Accepted(2, user, true);
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

    public Iterable<UserFlashcard> createRandomTest(String username) {
        User user = userDetailsService.loadUserByUsername(username);

        List<UserFlashcard> flashcards = (List<UserFlashcard>) userFlashcardRepository.findAllByUserAndFlashcard_Accepted(user, true);

        List<UserFlashcard> randomFlashcards = new ArrayList<>();
        List<UserFlashcard> copy = new ArrayList<>(flashcards);

        System.out.println(flashcards.size());

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < Math.min(20, flashcards.size()); i++) {
            randomFlashcards.add(copy.remove(rand.nextInt(copy.size())));
        }

        return randomFlashcards;
    }
}
