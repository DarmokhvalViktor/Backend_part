package com.darmokhval.Backend_part.model.entity.tests;

import com.darmokhval.Backend_part.model.dto.AnswerDTO;
import com.darmokhval.Backend_part.model.dto.SentenceDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sentences")
@Getter
@Setter
@NoArgsConstructor
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sentenceId;

    @Column(name = "contet")
    @NotEmpty(message = "field `taskSentence` should not be empty")
    @Size(min = 10, max = 300, message = "field should contain minimum - 10, maximum - 300 characters")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sentence")
    private List<Answer> answers;

    @ManyToOne()
    @JoinColumn(name = "worksheet_id")
    private Worksheet worksheet;

}
