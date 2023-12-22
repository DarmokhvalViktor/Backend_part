package com.darmokhval.Backend_part.dto;

import com.darmokhval.Backend_part.entity.tests.Answer;
import com.darmokhval.Backend_part.entity.tests.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private String answer;
    public static AnswerDTO convertToDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswer(answer.getAnswer());
        return answerDTO;
    }
}
