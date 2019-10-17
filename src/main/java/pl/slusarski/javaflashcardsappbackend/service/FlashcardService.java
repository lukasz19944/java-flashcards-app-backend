package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.User;
import pl.slusarski.javaflashcardsappbackend.domain.UserFlashcard;
import pl.slusarski.javaflashcardsappbackend.repository.FlashcardRepository;
import pl.slusarski.javaflashcardsappbackend.repository.UserFlashcardRepository;

import java.security.SecureRandom;
import java.util.*;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final UserService userService;
    private final UserFlashcardRepository userFlashcardRepository;

    @Autowired
    private UserFlashcardService userFlashcardService;

    public FlashcardService(FlashcardRepository flashcardRepository, UserService userService, UserFlashcardRepository userFlashcardRepository) {
        this.flashcardRepository = flashcardRepository;
        this.userService = userService;
        this.userFlashcardRepository = userFlashcardRepository;
    }

    public Iterable<Flashcard> findAllAcceptedFlashcards() {
        return flashcardRepository.findAllByAccepted(true);
    }

    public Iterable<Flashcard> findAllNotAcceptedFlashcards() {
        return flashcardRepository.findAllByAccepted(false);
    }

    public Iterable<String> findAllCategories() {
        return flashcardRepository.findAllCategories();
    }

    public Flashcard saveOrUpdateFlashcard(Flashcard flashcard) {
        flashcard.setAccepted(true);

        Flashcard newFlashcard = flashcardRepository.save(flashcard);

        createUserFlashcardForAll(newFlashcard);

        return newFlashcard;
    }

    private void createUserFlashcardForAll(Flashcard flashcard) {
        Iterable<User> users = userService.getAllUsers();

        for (User user : users) {
            UserFlashcard userFlashcard = new UserFlashcard();

            userFlashcard.setFlashcard(flashcard);
            userFlashcard.setKnowledgeLevel(0);
            userFlashcard.setUser(user);

            userFlashcardService.createOrUpdateKnowledgeLevel(userFlashcard);
        }
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
        Optional<Flashcard> flashcard = flashcardRepository.findById(id);

        userFlashcardService.deleteUserFlashcardByFlashcard(flashcard.get());

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

    public Flashcard proposeFlashcard(Flashcard flashcard) {
        flashcard.setAccepted(false);

        return flashcardRepository.save(flashcard);
    }

    public void acceptFlashcard(Long flashcardId) {
        Optional<Flashcard> flashcardOptional = flashcardRepository.findById(flashcardId);

        Flashcard flashcard = flashcardOptional.get();
        flashcard.setAccepted(true);

        Flashcard savedFlashcard = flashcardRepository.save(flashcard);

        createUserFlashcardForAll(savedFlashcard);
    }

    public void rejectFlashcard(Long flashcardId) {
        flashcardRepository.delete(findFlashcardById(flashcardId));
    }
}
