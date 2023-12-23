package com.darmokhval.Backend_part.entity.tests;

import com.darmokhval.Backend_part.dto.AnswerDTO;
import com.darmokhval.Backend_part.dto.TestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "task_sentence")
    private String taskSentence;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private List<Answer> answers;

    @Column(name = "right_answer")
    private String rightAnswer;

    @ManyToOne()
    @JoinColumn(name = "test_container_id")
    private TestContainer testContainer;

    public static Test convertToEntity(TestDTO testDTO) {
        Test test = new Test();
        test.setRightAnswer(testDTO.getRightAnswer());
        test.setTaskSentence(testDTO.getTaskSentence());
        List<Answer> answerList = new ArrayList<>();

        for (AnswerDTO answer: testDTO.getAnswers()) {
            Answer answer2 = Answer.convertToEntity(answer);
            answer2.setTest(test);
            answerList.add(answer2);
        }
        test.setAnswers(answerList);

        return test;
    }
}
