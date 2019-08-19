package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;

@Repository
public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {

}