package com.darmokhval.Backend_part.dto;

import com.darmokhval.Backend_part.entity.tests.Test;
import com.darmokhval.Backend_part.entity.tests.TestContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestContainerDTO {
    private String title;
    private String grade;
    private List<TestDTO> testList;

    public static TestContainerDTO convertToDTO(TestContainer testContainer) {
        TestContainerDTO testContainerDTO = new TestContainerDTO();
        testContainerDTO.setTitle(testContainer.getTitle());
        testContainerDTO.setGrade(testContainer.getGrade());
        List<TestDTO> testDTOList = new ArrayList<>();
        for(Test test: testContainer.getTestList()) {
            TestDTO testDTO = TestDTO.convertToDTO(test);
            testDTOList.add(testDTO);
        }
        testContainerDTO.setTestList(testDTOList);
        return testContainerDTO;
    }
}
