package com.darmokhval.Backend_part.model.dto;

import com.darmokhval.Backend_part.model.entity.tests.Answer;
import lombok.*;

import java.util.Objects;

public class AnswerDTO {
    private Integer answerId;
    private String answerContent;
    private Boolean isCorrect;
    private Integer sentenceId;

    public AnswerDTO() {
    }

    public AnswerDTO(Integer answerId, String answerContent, Boolean isCorrect, Integer sentenceId) {
        this.answerId = answerId;
        this.answerContent = answerContent;
        this.isCorrect = isCorrect;
        this.sentenceId = sentenceId;
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

    public Integer getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(Integer sentenceId) {
        this.sentenceId = sentenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerDTO answerDTO = (AnswerDTO) o;

        if (!Objects.equals(answerId, answerDTO.answerId)) return false;
        if (!answerContent.equals(answerDTO.answerContent)) return false;
        if (!isCorrect.equals(answerDTO.isCorrect)) return false;
        return Objects.equals(sentenceId, answerDTO.sentenceId);
    }

    @Override
    public int hashCode() {
        int result = answerId != null ? answerId.hashCode() : 0;
        result = 31 * result + answerContent.hashCode();
        result = 31 * result + isCorrect.hashCode();
        result = 31 * result + (sentenceId != null ? sentenceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "answerId=" + answerId +
                ", answerContent='" + answerContent + '\'' +
                ", isCorrect=" + isCorrect +
                ", sentenceId=" + sentenceId +
                '}';
    }
}
