package com.darmokhval.Backend_part.service;

import com.darmokhval.Backend_part.exception.WorksheetNotFoundException;
import com.darmokhval.Backend_part.mapper.MainMapper;
import com.darmokhval.Backend_part.model.dto.WorksheetDTO;
import com.darmokhval.Backend_part.model.entity.tests.*;
import com.darmokhval.Backend_part.repository.QuestionTypeRepository;
import com.darmokhval.Backend_part.repository.WorksheetRepository;
import com.darmokhval.Backend_part.repository.WorksheetSpecification;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorksheetService {

    private final WorksheetRepository worksheetRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final MainMapper mainMapper;


    @Transactional
    public WorksheetDTO saveWorksheet(WorksheetDTO worksheetDTO) {
        Worksheet worksheet = mainMapper.convertWorksheetDTOToEntity(worksheetDTO);
        return mainMapper.convertWorksheetToDTO(worksheetRepository.save(worksheet));
    }

    private List<WorksheetDTO> getAllWorksheets() {
        List<Worksheet> worksheets = worksheetRepository.findAll();
        List<WorksheetDTO> worksheetDTOList = new ArrayList<>();
        for (Worksheet worksheet : worksheets) {
            WorksheetDTO testContainerDTO = mainMapper.convertWorksheetToDTO(worksheet);
            worksheetDTOList.add(testContainerDTO);
        }
        return worksheetDTOList;
    }

    public List<WorksheetDTO> getWorksheetsWithFilters(Map<String, String> filters) {
        if(filters.isEmpty()) {
            return getAllWorksheets();
        } else {
            String grade = filters.get("grade");
            String subject = filters.get("subject");
            String title = filters.get("title");
            System.out.println(grade + title + subject);
            Specification<Worksheet> spec = WorksheetSpecification.withFilters(grade, subject, title);

            List<Worksheet> filteredWorksheets = worksheetRepository.findAll(spec);
            List<WorksheetDTO> worksheetDTOList = new ArrayList<>();
            for (Worksheet worksheet : filteredWorksheets) {
                WorksheetDTO testContainerDTO = mainMapper.convertWorksheetToDTO(worksheet);
                worksheetDTOList.add(testContainerDTO);
            }
            return worksheetDTOList;
        }
    }

    public WorksheetDTO getWorksheetById(Integer id) {
        return mainMapper.convertWorksheetToDTO(worksheetRepository.findById(id)
                .orElseThrow(() -> new WorksheetNotFoundException(id)));
    }

    @Transactional
    public WorksheetDTO updateWorksheet(WorksheetDTO worksheetDTO, Integer id) {
        WorksheetDTO existedWorksheetDTO = getWorksheetById(id);
        existedWorksheetDTO.setTitle(worksheetDTO.getTitle());
        existedWorksheetDTO.setClassYear(worksheetDTO.getClassYear());
        existedWorksheetDTO.setSentences(worksheetDTO.getSentences());

        return saveWorksheet(existedWorksheetDTO);
    }

    @Transactional
    public WorksheetDTO deleteWorksheet(Integer id) {
        WorksheetDTO deletedWorksheet = getWorksheetById(id);
        worksheetRepository.deleteById(id);
        return deletedWorksheet;
    }

    public List<QuestionType> getAllQuestionTypes() {
        return questionTypeRepository.findAll();
    }

}
