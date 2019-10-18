package pl.slusarski.javaflashcardsappbackend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Flashcard flashcard;

    @Column(length = 3000)
    @NotBlank(message = "Powyższe pole jest wymagane")
    private String message;

    public Ticket() {

    }

    public Ticket(Flashcard flashcard, String message) {
        this.flashcard = flashcard;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
