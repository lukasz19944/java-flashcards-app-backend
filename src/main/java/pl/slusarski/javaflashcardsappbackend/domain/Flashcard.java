package pl.slusarski.javaflashcardsappbackend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Powyższe pole jest wymagane")
    private String question;
    @Column(length = 3000)
    @NotBlank(message = "Powyższe pole jest wymagane")
    private String answer;
    @NotBlank(message = "Powyższe pole jest wymagane")
    private String category;

    private boolean accepted;

    private Difficulty difficulty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flashcard)) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(id, flashcard.id) &&
                Objects.equals(question, flashcard.question) &&
                Objects.equals(answer, flashcard.answer) &&
                Objects.equals(category, flashcard.category) &&
                difficulty == flashcard.difficulty;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, question, answer, category, difficulty);
    }
}
