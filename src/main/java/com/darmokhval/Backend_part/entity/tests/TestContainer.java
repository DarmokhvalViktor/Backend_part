package com.darmokhval.Backend_part.entity.tests;

import com.darmokhval.Backend_part.dto.TestContainerDTO;
import com.darmokhval.Backend_part.dto.TestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_Containers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "test_title")
    private String title;

    @Column(name = "test_instruction")
    private String testInstruction;

    @Column(name = "grade")
    private String grade;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testContainer")
    private List<Test> testList;

    public static TestContainer convert(TestContainerDTO testContainerDTO) {
        TestContainer testContainer = new TestContainer();
        testContainer.setTitle(testContainerDTO.getTitle());
        testContainer.setGrade(testContainerDTO.getGrade());
        testContainer.setTestInstruction(testContainerDTO.getTestInstruction());
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