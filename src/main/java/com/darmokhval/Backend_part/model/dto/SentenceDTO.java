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
    private List<AnswerDTO> answers;
}
