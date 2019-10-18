package pl.slusarski.javaflashcardsappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.slusarski.javaflashcardsappbackend.domain.UserFlashcard;
import pl.slusarski.javaflashcardsappbackend.service.ErrorService;
import pl.slusarski.javaflashcardsappbackend.service.UserFlashcardService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/flashcard")
public class UserFlashcardController {

    private final UserFlashcardService userFlashcardService;
    private final ErrorService errorService;

    public UserFlashcardController(UserFlashcardService userFlashcardService, ErrorService errorService) {
        this.userFlashcardService = userFlashcardService;
        this.errorService = errorService;
    }

    @GetMapping("/category/{category}")
    public Iterable<UserFlashcard> findAllFlashcardsByCategoryAndKnowledgeLevel(@PathVariable String category, Principal principal) {
        return userFlashcardService.findAllFlashcardsByCategoryAndKnowledgeLevel(category, principal.getName());
    }

    @GetMapping("/category/{category}/{difficulty}")
    public Iterable<UserFlashcard> findAllFlashcardsByCategoryAndKnowledgeLevelAndDifficulty(@PathVariable String category, @PathVariable String difficulty, Principal principal) {
        return userFlashcardService.findAllFlashcardsByCategoryAndKnowledgeLevelAndDifficulty(category, difficulty, principal.getName());
    }

    @GetMapping("/count/level")
    public long countAllFlashcardsByKnowledge(Principal principal) {
        return userFlashcardService.countAllFlashcardsByKnowledge(principal.getName());
    }

    @GetMapping("/count/category-level/")
    public Map<String, Integer> countAllFlashcardsByCategoryAndKnowledge(Principal principal) {
        return userFlashcardService.countAllFlashcardsByCategoryAndKnowledgeLevel(principal.getName());
    }

    @GetMapping("/reset/")
    public void resetProgress(Principal principal) {
        userFlashcardService.resetProgress(principal.getName());
    }


    @PostMapping("/updateKnowledge")
    public ResponseEntity<?> updateKnowledgeLevel(@Valid @RequestBody UserFlashcard userFlashcard, BindingResult result) {
        ResponseEntity<?> errorMap = errorService.mapValidationService(result);

        if (errorMap != null) {
            return errorMap;
        }

        UserFlashcard newUserFlashcard = userFlashcardService.createOrUpdateKnowledgeLevel(userFlashcard);

        return new ResponseEntity<>(newUserFlashcard, HttpStatus.CREATED);
    }

    @GetMapping("/random/")
    public Iterable<UserFlashcard> createRandomTest(Principal principal) {
        return userFlashcardService.createRandomTest(principal.getName());
    }

}
