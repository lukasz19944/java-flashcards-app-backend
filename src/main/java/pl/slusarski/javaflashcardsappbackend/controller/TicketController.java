package pl.slusarski.javaflashcardsappbackend.controller;

import org.springframework.web.bind.annotation.*;
import pl.slusarski.javaflashcardsappbackend.domain.Ticket;
import pl.slusarski.javaflashcardsappbackend.service.ErrorService;
import pl.slusarski.javaflashcardsappbackend.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final ErrorService errorService;

    public TicketController(TicketService ticketService, ErrorService errorService) {
        this.ticketService = ticketService;
        this.errorService = errorService;
    }

    @PostMapping("")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }
}
