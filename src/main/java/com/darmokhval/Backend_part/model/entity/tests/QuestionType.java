package com.darmokhval.Backend_part.model.entity.tests;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_type_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true)
    private EQuestionType type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionType")
    private List<Sentence> sentence;

}
