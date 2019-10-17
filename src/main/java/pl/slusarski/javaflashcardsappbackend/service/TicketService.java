package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.stereotype.Service;
import pl.slusarski.javaflashcardsappbackend.domain.Ticket;
import pl.slusarski.javaflashcardsappbackend.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlashcardService flashcardService;

    public TicketService(TicketRepository ticketRepository, FlashcardService flashcardService) {
        this.ticketRepository = ticketRepository;
        this.flashcardService = flashcardService;
    }

    public Ticket createTicket(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }
}
