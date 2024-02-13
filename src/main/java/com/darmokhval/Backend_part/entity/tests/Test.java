package com.darmokhval.Backend_part.entity.tests;

import com.darmokhval.Backend_part.dto.AnswerDTO;
import com.darmokhval.Backend_part.dto.TestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "task_sentence")
    @NotEmpty(message = "field `taskSentence` should not be empty")
    @Size(min = 10, max = 300, message = "field should contain minimum - 10, maximum - 300 characters")
    private String taskSentence;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private List<Answer> answers;

    @Column(name = "right_answer")
    @NotEmpty(message = "field `rightAnswer` should not be empty")
    @Size(min = 1, max = 127, message = "field should contain minimum - 1, maximum - 127 characters")
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
