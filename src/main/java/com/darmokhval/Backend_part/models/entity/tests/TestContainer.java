package com.darmokhval.Backend_part.models.entity.tests;

import com.darmokhval.Backend_part.models.dto.TestContainerDTO;
import com.darmokhval.Backend_part.models.dto.TestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_Containers")
@Getter
@Setter
@NoArgsConstructor
public class TestContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "test_title")
    @NotEmpty(message = "field `title` should not be empty")
    @Size(min = 1, max = 127, message = "field should contain minimum - 1, maximum - 127 characters")
    private String title;

    @Column(name = "test_instruction")
    @NotEmpty(message = "field `testInstruction` should not be empty")
    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
    private String testInstruction;

    @Column(name = "grade")
    @NotEmpty(message = "field `grade` should not be empty")
    @Size(min = 1, max = 12)
    private String grade;

    @Column(name = "subject")
    @NotEmpty(message = "field `subject` should not be empty")
    @Size(min = 1, max = 50, message = "field should contain minimum - 1, maximum - 50 characters")
    private String subject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testContainer")
    private List<Test> testList;

    public static TestContainer convert(TestContainerDTO testContainerDTO) {
        TestContainer testContainer = new TestContainer();
        testContainer.setTitle(testContainerDTO.getTitle());
        testContainer.setGrade(testContainerDTO.getGrade());
        testContainer.setTestInstruction(testContainerDTO.getTestInstruction());
        testContainer.setSubject(testContainerDTO.getSubject());
        List<Test> newList = new ArrayList<>();

        for(TestDTO test: testContainerDTO.getTestList()) {
            Test test2 = Test.convertToEntity(test);
            test2.setTestContainer(testContainer);
            newList.add(test2);
        }
        testContainer.setTestList(newList);
        return testContainer;
    }
}