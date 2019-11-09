package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slusarski.javaflashcardsappbackend.domain.Flashcard;
import pl.slusarski.javaflashcardsappbackend.domain.Ticket;
import pl.slusarski.javaflashcardsappbackend.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<Ticket> findAllTickets() {
        return this.ticketRepository.findAll();
    }

    public void deleteTicket(Long ticketId) {
        this.ticketRepository.deleteById(ticketId);
    }

    @Transactional
    public void deleteTicketsByFlashcard(Flashcard flashcard) {
        ticketRepository.deleteAllByFlashcard(flashcard);
    }

    public Ticket findTicketById(Long ticketId) {
        return this.ticketRepository.findById(ticketId).get();
    }
}
