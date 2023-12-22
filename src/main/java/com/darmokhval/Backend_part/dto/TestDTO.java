package com.darmokhval.Backend_part.dto;

import com.darmokhval.Backend_part.entity.tests.Answer;
import com.darmokhval.Backend_part.entity.tests.Test;
import com.darmokhval.Backend_part.entity.tests.TestContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private String testText;
    private String rightAnswer;
    private List<AnswerDTO> answers;
    private TestContainer testContainer;

//    private int testContainerId;

    public static TestDTO convertToDTO(Test test) {
        TestDTO testDTO = new TestDTO();
        testDTO.setTestText(test.getTestText());
        testDTO.setRightAnswer(test.getRightAnswer());
        testDTO.setTestContainer(test.getTestContainer());

        List<AnswerDTO> answerDTOS = new ArrayList<>();
        for(Answer answer: test.getAnswers()) {
            AnswerDTO answerDTO = AnswerDTO.convertToDTO(answer);
            answerDTO.setTest(test);
            answerDTOS.add(answerDTO);
        }
//        testDTO.setTestContainerId(test.getTestContainer().getId());
        testDTO.setAnswers(answerDTOS);
        return testDTO;
    }
}
