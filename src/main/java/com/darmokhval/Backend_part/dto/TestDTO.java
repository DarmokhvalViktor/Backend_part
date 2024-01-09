package com.darmokhval.Backend_part.dto;

import com.darmokhval.Backend_part.entity.tests.Answer;
import com.darmokhval.Backend_part.entity.tests.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private int id;
    private String taskSentence;
    private String rightAnswer;
    private List<AnswerDTO> answers;


    public static TestDTO convertToDTO(Test test) {
        TestDTO testDTO = new TestDTO();
        testDTO.setTaskSentence(test.getTaskSentence());
        testDTO.setRightAnswer(test.getRightAnswer());
        testDTO.setId(test.getId());

        List<AnswerDTO> answerDTOS = new ArrayList<>();
        for(Answer answer: test.getAnswers()) {
            AnswerDTO answerDTO = AnswerDTO.convertToDTO(answer);
            answerDTOS.add(answerDTO);
        }
        testDTO.setAnswers(answerDTOS);
        return testDTO;
    }
}
