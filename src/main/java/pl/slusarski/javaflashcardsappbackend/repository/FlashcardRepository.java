package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;

@Repository
public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {

    @Query("SELECT DISTINCT f.category FROM Flashcard f")
    Iterable<String> findAllCategories();

    Iterable<Flashcard> findAllByCategoryAndKnowledgeLevelIn(String category, int[] knowledgeLevels);

    long count();

    long countByKnowledgeLevel(int level);
}