package pl.slusarski.javaflashcardsappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.slusarski.javaflashcardsappbackend.domain.Ticket;
import pl.slusarski.javaflashcardsappbackend.service.ErrorService;
import pl.slusarski.javaflashcardsappbackend.service.TicketService;

import javax.validation.Valid;

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
    public ResponseEntity<?> createTicket(@Valid @RequestBody Ticket ticket, BindingResult result) {
        ResponseEntity<?> errorMap = errorService.mapValidationService(result);

        if (errorMap != null) {
            return errorMap;
        }

        Ticket newTicket = ticketService.createTicket(ticket);

        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Iterable<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{ticketId}")
    public Ticket getTicket(@PathVariable String ticketId) {
        return ticketService.findTicketById(Long.parseLong(ticketId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ticketId}")
    public void deleteTicket(@PathVariable String ticketId) {
        ticketService.deleteTicket(Long.parseLong(ticketId));
    }
}
