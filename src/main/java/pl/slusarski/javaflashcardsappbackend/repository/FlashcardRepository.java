package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;

import java.util.List;

@Repository
public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {

    Iterable<Flashcard> findAllByAccepted(boolean accepted);

    @Query("SELECT DISTINCT f.category FROM Flashcard f WHERE f.accepted = 1")
    Iterable<String> findAllCategories();

    long countAllByAccepted(boolean accepted);

    @Query("SELECT f.category, COUNT(f) FROM Flashcard f WHERE f.accepted = 1 GROUP BY f.category")
    List<Object[]> countByCategory();



}