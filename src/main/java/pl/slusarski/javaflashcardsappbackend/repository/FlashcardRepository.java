package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;

import java.util.List;

@Repository
public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {

    @Query("SELECT DISTINCT f.category FROM Flashcard f")
    Iterable<String> findAllCategories();

    Iterable<Flashcard> findAllByCategoryAndKnowledgeLevelIn(String category, int[] knowledgeLevels);

    long count();

    long countByKnowledgeLevel(int level);

    @Query("SELECT f.category, COUNT(f) FROM Flashcard f GROUP BY f.category")
    List<Object[]> countByCategory();

    @Query("SELECT f.category, COUNT(f) FROM Flashcard f WHERE f.knowledgeLevel = 2 GROUP BY f.category")
    List<Object[]> countByCategoryAndKnowledgeLevel();

}