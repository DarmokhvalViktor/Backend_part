package com.darmokhval.Backend_part.models.dto;

import com.darmokhval.Backend_part.models.entity.tests.Answer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private int id;
    private String answer;
    public static AnswerDTO convertToDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setAnswer(answer.getAnswer());
        return answerDTO;
    }
}