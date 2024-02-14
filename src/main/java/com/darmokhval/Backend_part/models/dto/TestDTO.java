package com.darmokhval.Backend_part.models.dto;

import com.darmokhval.Backend_part.models.entity.tests.Answer;
import com.darmokhval.Backend_part.models.entity.tests.Test;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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