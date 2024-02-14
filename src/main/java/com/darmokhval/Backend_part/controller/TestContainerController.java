package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.models.dto.TestContainerDTO;
import com.darmokhval.Backend_part.exceptions.TestContainerNotFoundException;
import com.darmokhval.Backend_part.services.TestContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestContainerController {
    private final TestContainerService testContainerService;

    @Autowired
    public TestContainerController(TestContainerService testContainerService) {
        this.testContainerService = testContainerService;
    }

    @GetMapping("/tests")
    public ResponseEntity<List<TestContainerDTO>> getAllTests() {
        List<TestContainerDTO> testContainerDTOList = testContainerService.getAllTestContainers();
        if (testContainerDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(testContainerDTOList, HttpStatus.OK);
        }
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<TestContainerDTO> getTestById(@PathVariable Integer id) {
        try {
            TestContainerDTO testContainerDTO = testContainerService.getTestContainerById(id);
            return new ResponseEntity<>(testContainerDTO, HttpStatus.OK);
        } catch (TestContainerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tests/grade/{grade}")
    public ResponseEntity<List<TestContainerDTO>> getTestsByGrade(@PathVariable String grade) {
        try {
            List<TestContainerDTO> testContainerDTOList = testContainerService.getTestContainerByGrade(grade);
            return new ResponseEntity<>(testContainerDTOList, HttpStatus.OK);
        } catch (TestContainerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tests")
    public ResponseEntity<TestContainerDTO> addTest(@RequestBody TestContainerDTO testContainerDTO) {
        TestContainerDTO savedTestContainerDTO = testContainerService.saveTestContainer(testContainerDTO);
        return new ResponseEntity<>(savedTestContainerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/tests/{id}")
    public ResponseEntity<TestContainerDTO> updateTest(@RequestBody TestContainerDTO testContainerDTO, @PathVariable Integer id) {
        try {
            return new ResponseEntity<>(testContainerService.updateTestContainer(testContainerDTO, id), HttpStatus.OK);
        } catch (TestContainerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/tests/{id}")
    public ResponseEntity<TestContainerDTO> partialUpdateTest(@RequestBody Map<String, Object> valuesToUpdate, @PathVariable Integer id) {
        try {
            return new ResponseEntity<>(testContainerService.partialUpdateTestContainer(valuesToUpdate, id), HttpStatus.OK);
        } catch (TestContainerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("tests/{id}")
    public ResponseEntity<TestContainerDTO> deleteTest(@PathVariable Integer id) {
        try {
            return  new ResponseEntity<>(testContainerService.deleteTestContainer(id), HttpStatus.OK);
        } catch (TestContainerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
