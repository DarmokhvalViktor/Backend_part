package com.darmokhval.Backend_part.model.dto;

import com.darmokhval.Backend_part.model.entity.tests.Answer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private Integer answerId;
    private String answerContent;
    private Boolean isCorrect;
    private Integer sentenceId;
}
