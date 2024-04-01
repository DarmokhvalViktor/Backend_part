package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.model.dto.WorksheetDTO;
import com.darmokhval.Backend_part.exception.WorksheetNotFoundException;
import com.darmokhval.Backend_part.model.entity.tests.QuestionType;
import com.darmokhval.Backend_part.service.WorksheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WorksheetController {
    private final WorksheetService worksheetService;

    @GetMapping("/worksheets")
    public ResponseEntity<List<WorksheetDTO>> getAllWorksheets(@RequestParam Map<String, String> params) {
        List<WorksheetDTO> worksheetDTOList = worksheetService.getWorksheetsWithFilters(params);
        return new ResponseEntity<>(worksheetDTOList, HttpStatus.OK);
    }

    @GetMapping("/worksheets/{id}")
    public ResponseEntity<WorksheetDTO> getWorksheetById(@PathVariable Integer id) {
        WorksheetDTO worksheetDTO = worksheetService.getWorksheetById(id);
        return new ResponseEntity<>(worksheetDTO, HttpStatus.OK);
    }

    @PostMapping("/worksheets")
    public ResponseEntity<WorksheetDTO> addTest(@RequestBody WorksheetDTO worksheetDTO) {
        WorksheetDTO savedWorksheetDTO = worksheetService.saveWorksheet(worksheetDTO);
        return new ResponseEntity<>(savedWorksheetDTO, HttpStatus.CREATED);
    }

    @PutMapping("/worksheets/{id}")
    public ResponseEntity<WorksheetDTO> updateTest(@RequestBody WorksheetDTO worksheetDTO, @PathVariable Integer id) {
        return new ResponseEntity<>(worksheetService.updateWorksheet(worksheetDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("worksheets/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Integer id) {
        return  new ResponseEntity<>(worksheetService.deleteWorksheet(id), HttpStatus.OK);
    }

    @GetMapping("question_types")
    public ResponseEntity<List<QuestionType>> getAllQuestionTypes() {
        return ResponseEntity.ok(worksheetService.getAllQuestionTypes());
    }

}
