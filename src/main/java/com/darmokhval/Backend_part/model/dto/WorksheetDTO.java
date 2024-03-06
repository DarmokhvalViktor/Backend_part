package com.darmokhval.Backend_part.model.dto;

import com.darmokhval.Backend_part.model.entity.tests.Sentence;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorksheetDTO {
    private Integer worksheetId;
    private String title;
    private String classYear;
    private String instruction;
    private String subject;
    private List<SentenceDTO> sentences;
}
