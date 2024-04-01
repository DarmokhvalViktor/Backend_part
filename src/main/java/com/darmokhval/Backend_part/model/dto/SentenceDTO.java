package com.darmokhval.Backend_part.model.dto;

import com.darmokhval.Backend_part.model.entity.tests.Answer;
import com.darmokhval.Backend_part.model.entity.tests.Sentence;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceDTO {
    private Integer sentenceId;
    private String content;
    private String questionType;
    private Integer worksheetId;
    private List<AnswerDTO> answers;

    public void addAnswerDTO(AnswerDTO answerDTO) {
        this.answers.add(answerDTO);
    }

    public void removeAnswerDTO(Integer id) {
        this.answers.removeIf(answerDTO -> answerDTO.getAnswerId().equals(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SentenceDTO that = (SentenceDTO) o;

        if (!sentenceId.equals(that.sentenceId)) return false;
        if (!content.equals(that.content)) return false;
        if (!questionType.equals(that.questionType)) return false;
        if (!worksheetId.equals(that.worksheetId)) return false;
        return answers.equals(that.answers);
    }

    @Override
    public int hashCode() {
        int result = sentenceId.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + questionType.hashCode();
        result = 31 * result + worksheetId.hashCode();
        result = 31 * result + answers.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SentenceDTO{" +
                "sentenceId=" + sentenceId +
                ", content='" + content + '\'' +
                ", questionType='" + questionType + '\'' +
                ", worksheetId=" + worksheetId +
                ", answers=" + answers +
                '}';
    }
}
