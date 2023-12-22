package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.dto.TestContainerDTO;
import com.darmokhval.Backend_part.entity.tests.TestContainer;
import com.darmokhval.Backend_part.services.TestContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestContainerController {
    private final TestContainerService testContainerService;

    @Autowired
    public TestContainerController(TestContainerService testContainerService) {
        this.testContainerService = testContainerService;
    }

    @GetMapping("/tests")
    public List<TestContainerDTO> getAllTests() {
        return testContainerService.getAllTestContainers();
    }

//    TestContainerDTO
    @PostMapping("/addTest")
    public ResponseEntity<TestContainerDTO> addTest(@RequestBody TestContainerDTO testContainerDTO) {
        TestContainerDTO savedTestContainerDTO =  testContainerService.saveTestContainer(testContainerDTO);
        return new ResponseEntity<>(savedTestContainerDTO, HttpStatus.CREATED);
    }
}
