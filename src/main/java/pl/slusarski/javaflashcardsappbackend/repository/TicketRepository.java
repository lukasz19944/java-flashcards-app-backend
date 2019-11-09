package pl.slusarski.javaflashcardsappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    void deleteAllByFlashcard(Flashcard flashcard);
}
