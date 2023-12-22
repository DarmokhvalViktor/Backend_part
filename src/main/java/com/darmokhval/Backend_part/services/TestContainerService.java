package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.dto.TestContainerDTO;
import com.darmokhval.Backend_part.entity.tests.TestContainer;
import com.darmokhval.Backend_part.repository.TestContainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TestContainerService {

    private final TestContainerRepository testContainerRepository;

    @Autowired
    public TestContainerService(TestContainerRepository testContainerRepository) {
        this.testContainerRepository = testContainerRepository;
    }

    public TestContainerDTO saveTestContainer(TestContainerDTO testContainerDTO) {
        TestContainer testContainer = TestContainer.convert(testContainerDTO);

        return TestContainerDTO.convertToDTO(testContainerRepository.save(testContainer));
    }

    public List<TestContainerDTO> getAllTestContainers() {
        List<TestContainer> testContainers = testContainerRepository.findAll();
        List<TestContainerDTO> testContainerDTOList = new ArrayList<>();
        for(TestContainer testContainer: testContainers) {
            TestContainerDTO testContainerDTO = TestContainerDTO.convertToDTO(testContainer);
            testContainerDTOList.add(testContainerDTO);
        }

        return testContainerDTOList;
    }
}
