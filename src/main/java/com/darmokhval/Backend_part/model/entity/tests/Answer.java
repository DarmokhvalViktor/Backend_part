package com.darmokhval.Backend_part.model.entity.tests;

import com.darmokhval.Backend_part.model.dto.AnswerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Table(name = "answers")
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @Column(name = "answer")
    @NotEmpty(message = "field `answer` should not be empty")
    @Size(min = 1, max = 127, message = "field should contain minimum - 1, maximum - 127 characters")
    private String answerContent;

    @Column(name = "is_Correct")
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sentence_id")
    private Sentence sentence;

    public Answer() {
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (!Objects.equals(answerId, answer.answerId)) return false;
        if (!answerContent.equals(answer.answerContent)) return false;
        if (!isCorrect.equals(answer.isCorrect)) return false;
        return Objects.equals(sentence, answer.sentence);
    }

    @Override
    public int hashCode() {
        int result = answerId != null ? answerId.hashCode() : 0;
        result = 31 * result + answerContent.hashCode();
        result = 31 * result + isCorrect.hashCode();
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        return result;
    }
}
