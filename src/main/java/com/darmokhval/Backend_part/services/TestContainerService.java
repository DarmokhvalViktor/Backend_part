package com.darmokhval.Backend_part.services;

import com.darmokhval.Backend_part.dto.TestContainerDTO;
import com.darmokhval.Backend_part.dto.TestDTO;
import com.darmokhval.Backend_part.entity.tests.TestContainer;
import com.darmokhval.Backend_part.errors.TestContainerNotFoundException;
import com.darmokhval.Backend_part.repository.TestContainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        for (TestContainer testContainer : testContainers) {
            TestContainerDTO testContainerDTO = TestContainerDTO.convertToDTO(testContainer);
            testContainerDTOList.add(testContainerDTO);
        }
        return testContainerDTOList;
    }

    public TestContainerDTO getTestContainerById(Integer id) {
        return TestContainerDTO.convertToDTO(testContainerRepository.findById(id)
                .orElseThrow(() -> new TestContainerNotFoundException(id)));
    }


    public List<TestContainerDTO> getTestContainerByGrade(String grade) {
        List<TestContainer> testContainerList = testContainerRepository.findByGrade(grade);
        if(testContainerList.isEmpty()) {
            throw new TestContainerNotFoundException(grade);
        } else {
            return testContainerList.stream().map(TestContainerDTO::convertToDTO).toList();
        }
    }

    public TestContainerDTO updateTestContainer(TestContainerDTO testContainerDTO, Integer id) {
//        Invoking getTestContainerById method, which searches for entity by id; What about productivity? Maybe faster via repository.findById(id)?
        TestContainerDTO existedContainerDTO = getTestContainerById(id);
        existedContainerDTO.setTitle(testContainerDTO.getTitle());
        existedContainerDTO.setGrade(testContainerDTO.getGrade());
        existedContainerDTO.setTestList(testContainerDTO.getTestList());

        return saveTestContainer(existedContainerDTO);
    }

//    I'm not sure if this part of code violates Java principles (because of type casting)
//    TODO change from saveTEstContainer to Update testcontainer to actually update + add rightaAnswer and answer
    public TestContainerDTO partialUpdateTestContainer(Map<String, Object> valuesToUpdate, Integer id) {
        TestContainerDTO existedContainerDTO = getTestContainerById(id);
        valuesToUpdate.forEach((key, value) -> {
            switch(key) {
                case "title":
                    existedContainerDTO.setTitle((String) value);
                    break;
                case "grade":
                    existedContainerDTO.setGrade((String) value);
                    break;
                case "testList":
                    existedContainerDTO.setTestList((List<TestDTO>) value);
            }
        });
        return saveTestContainer(existedContainerDTO);
    }

    public TestContainerDTO deleteTestContainer(Integer id) {
        TestContainerDTO deletedContainer = getTestContainerById(id);
        testContainerRepository.deleteById(id);
        return deletedContainer;
    }

}
