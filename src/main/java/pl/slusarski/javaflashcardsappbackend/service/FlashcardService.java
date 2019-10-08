package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.User;
import pl.slusarski.javaflashcardsappbackend.domain.UserFlashcard;
import pl.slusarski.javaflashcardsappbackend.repository.FlashcardRepository;

import java.security.SecureRandom;
import java.util.*;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final UserService userService;

    @Autowired
    private UserFlashcardService userFlashcardService;

    public FlashcardService(FlashcardRepository flashcardRepository, UserService userService) {
        this.flashcardRepository = flashcardRepository;
        this.userService = userService;
    }

    public Iterable<Flashcard> findAllFlashcards() {
        return flashcardRepository.findAll();
    }

    public Iterable<String> findAllCategories() {
        return flashcardRepository.findAllCategories();
    }

    public Flashcard saveOrUpdateFlashcard(Flashcard flashcard) {
        Iterable<User> users = userService.getAllUsers();

        Flashcard newFlashcard = flashcardRepository.save(flashcard);

        for (User user : users) {
            UserFlashcard userFlashcard = new UserFlashcard();

            userFlashcard.setFlashcard(newFlashcard);
            userFlashcard.setKnowledgeLevel(0);
            userFlashcard.setUser(user);

            userFlashcardService.createOrUpdateKnowledgeLevel(userFlashcard);
        }

        return newFlashcard;
    }

    public long countAllFlashcards() {
        return flashcardRepository.count();
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
