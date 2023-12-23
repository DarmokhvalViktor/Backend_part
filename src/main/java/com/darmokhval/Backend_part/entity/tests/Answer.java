package com.darmokhval.Backend_part.entity.tests;

import com.darmokhval.Backend_part.dto.AnswerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "answers")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "answer")
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
