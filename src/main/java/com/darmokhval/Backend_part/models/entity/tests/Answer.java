package com.darmokhval.Backend_part.models.entity.tests;

import com.darmokhval.Backend_part.models.dto.AnswerDTO;
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
    private int id;

    @Column(name = "answer")
    @NotEmpty(message = "field `answer` should not be empty")
    @Size(min = 1, max = 127, message = "field should contain minimum - 1, maximum - 127 characters")
    private String answer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id")
    private Test test;

    public static Answer convertToEntity(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setAnswer(answerDTO.getAnswer());
        return answer;
    }
}
