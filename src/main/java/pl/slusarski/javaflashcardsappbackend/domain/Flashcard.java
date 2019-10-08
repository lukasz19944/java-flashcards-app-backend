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

    //private int knowledgeLevel;

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

//    public int getKnowledgeLevel() {
//        return knowledgeLevel;
//    }
//
//    public void setKnowledgeLevel(int knowledgeLevel) {
//        this.knowledgeLevel = knowledgeLevel;
//    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

//    @PrePersist
//    protected void onCreate() {
//        this.knowledgeLevel = 0;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flashcard)) return false;
        Flashcard flashcard = (Flashcard) o;
        return //knowledgeLevel == flashcard.knowledgeLevel &&
                Objects.equals(id, flashcard.id) &&
                Objects.equals(question, flashcard.question) &&
                Objects.equals(answer, flashcard.answer) &&
                Objects.equals(category, flashcard.category) &&
                difficulty == flashcard.difficulty;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, question, answer, category, //knowledgeLevel,
                difficulty);
    }
}
