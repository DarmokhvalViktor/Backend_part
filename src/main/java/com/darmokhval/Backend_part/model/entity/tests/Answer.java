package com.darmokhval.Backend_part.model.entity.tests;

import com.darmokhval.Backend_part.model.dto.AnswerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "answers")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @Column(name = "answer")
    @NotEmpty(message = "field `answer` should not be empty")
    @Size(min = 1, max = 127, message = "field should contain minimum - 1, maximum - 127 characters")
    private String answerContent;

    @Column(name = "is_Correct")
    private Boolean isCorrect;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sentence_id")
    private Sentence sentence;
}
