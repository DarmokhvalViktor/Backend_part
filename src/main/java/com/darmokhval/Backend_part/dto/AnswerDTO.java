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
    private Test test;
    private int testId;

    public static AnswerDTO convertToDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswer(answer.getAnswer());
//        need to set not test, but Test_id, same to do with test_dto TODO
//        answerDTO.setTest(answer.getTest());
//        answerDTO.setTestId(answer.getTest().getId());
        return answerDTO;
    }
}
