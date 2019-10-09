package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.slusarski.javaflashcardsappbackend.domain.Difficulty;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.User;
import pl.slusarski.javaflashcardsappbackend.domain.UserFlashcard;

import java.util.List;

public interface UserFlashcardRepository extends CrudRepository<UserFlashcard, Long> {

    Iterable<UserFlashcard> findAllByFlashcard_CategoryAndKnowledgeLevelInAndUser(String category, int[] knowledgeLevel, User user);

    Iterable<UserFlashcard> findAllByFlashcard_CategoryAndKnowledgeLevelInAndFlashcard_DifficultyInAndUser(String category, int[] knowledgeLevels, Difficulty[] difficulties, User user);

    long countByKnowledgeLevelAndUser(int level, User user);

    @Query("SELECT uf.flashcard.category, COUNT(uf.flashcard.category) FROM UserFlashcard uf WHERE uf.knowledgeLevel = 2 AND uf.user.id = :id GROUP BY uf.flashcard.category")
    List<Object[]> countByFlashcardCategoryAndKnowledgeLevelAndUser(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserFlashcard uf SET uf.knowledgeLevel = 0 WHERE uf.user.id = :id")
    void resetProgress(@Param("id") Long id);

    void deleteAllByFlashcard(Flashcard flashcard);

}
