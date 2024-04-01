package com.darmokhval.Backend_part.service;

import com.darmokhval.Backend_part.exception.WorksheetNotFoundException;
import com.darmokhval.Backend_part.mapper.MainMapper;
import com.darmokhval.Backend_part.model.dto.AnswerDTO;
import com.darmokhval.Backend_part.model.dto.SentenceDTO;
import com.darmokhval.Backend_part.model.dto.WorksheetDTO;
import com.darmokhval.Backend_part.model.entity.tests.*;
import com.darmokhval.Backend_part.repository.QuestionTypeRepository;
import com.darmokhval.Backend_part.repository.WorksheetRepository;
import com.darmokhval.Backend_part.repository.WorksheetSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

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
            WorksheetDTO worksheetDTO = mainMapper.convertWorksheetToDTO(worksheet);
            worksheetDTOList.add(worksheetDTO);
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
                WorksheetDTO worksheetDTO = mainMapper.convertWorksheetToDTO(worksheet);
                worksheetDTOList.add(worksheetDTO);
            }
            return worksheetDTOList;
        }
    }

    @Transactional
    public WorksheetDTO getWorksheetById(Integer id) {
        return mainMapper.convertWorksheetToDTO(worksheetRepository.findById(id)
                .orElseThrow(() -> new WorksheetNotFoundException(id)));
    }

    @Transactional
    public WorksheetDTO updateWorksheet(WorksheetDTO worksheetDTO, Integer id) {
        Worksheet worksheet = worksheetRepository.findById(id)
                .orElseThrow(() -> new WorksheetNotFoundException(id));

        // Update worksheet details
        worksheet.setTitle(worksheetDTO.getTitle());
        worksheet.setSubject(worksheetDTO.getSubject());
        worksheet.setInstruction(worksheetDTO.getInstruction());
        worksheet.setClassYear(worksheetDTO.getClassYear());

        // Iterate through existing sentences
        List<Sentence> sentencesToRemove = new ArrayList<>();
        for (Sentence existingSentence : worksheet.getSentences()) {
            Optional<SentenceDTO> matchedNewSentence = worksheetDTO.getSentences().stream()
                    .filter(newSentence -> Objects.equals(newSentence.getSentenceId(), existingSentence.getSentenceId()))
                    .findFirst();

            if (matchedNewSentence.isPresent()) {
                SentenceDTO newSentenceDTO = matchedNewSentence.get();
                // Update existing sentence
                existingSentence.setContent(newSentenceDTO.getContent());
                existingSentence.setQuestionType(questionTypeRepository
                        .findByType(EQuestionType.valueOf(newSentenceDTO.getQuestionType()))
                        .orElseThrow(() -> new RuntimeException("Question type not found")));

                // Update existing answers or add new ones
                updateOrAddAnswers(existingSentence, newSentenceDTO.getAnswers());
            } else {
                // Add existing sentence to removal list if not found in the new worksheet
                sentencesToRemove.add(existingSentence);
            }
        }

        // Delete existing sentences not found in the updated worksheet
        sentencesToRemove.forEach(sentence -> worksheet.removeSentence(sentence.getSentenceId()));

        // Add new sentences to the worksheet
        addNewSentences(worksheet, worksheetDTO.getSentences());

        // Save and return updated worksheet
        return mainMapper.convertWorksheetToDTO(worksheetRepository.save(worksheet));
    }

    private void updateOrAddAnswers(Sentence existingSentence, List<AnswerDTO> newAnswersDTO) {
        List<Answer> answersToRemove = new ArrayList<>();
        for (Answer existingAnswer : existingSentence.getAnswers()) {
            Optional<AnswerDTO> matchedAnswerDTO = newAnswersDTO.stream()
                    .filter(answerDTO -> Objects.equals(answerDTO.getAnswerId(), existingAnswer.getAnswerId()))
                    .findFirst();
            if (matchedAnswerDTO.isPresent()) {
                // Update existing answer
                AnswerDTO newAnswerDTO = matchedAnswerDTO.get();
                existingAnswer.setAnswerContent(newAnswerDTO.getAnswerContent());
                existingAnswer.setIsCorrect(newAnswerDTO.getIsCorrect());
                // Remove matched answer DTO from the list of new answers
                newAnswersDTO.remove(newAnswerDTO);
            } else {
                // Add existing answer to removal list if not found in the new answers
                answersToRemove.add(existingAnswer);
            }
        }

        // Delete existing answers not found in the updated answers
        for(Answer answer: answersToRemove) {
            existingSentence.removeAnswer(answer.getAnswerId());
        }
//        answersToRemove.forEach(existingSentence::removeAnswer);

        // Add new answers to the existing sentence
        for (AnswerDTO newAnswerDTO : newAnswersDTO) {
            Answer newAnswer = new Answer();
            newAnswer.setAnswerContent(newAnswerDTO.getAnswerContent());
            newAnswer.setIsCorrect(newAnswerDTO.getIsCorrect());
            existingSentence.addAnswer(newAnswer);
        }
    }

    private void addNewSentences(Worksheet worksheet, List<SentenceDTO> newSentencesDTO) {
        for (SentenceDTO newSentenceDTO : newSentencesDTO) {
            if (newSentenceDTO.getSentenceId() == null) {
                Sentence newSentence = new Sentence();
                newSentence.setContent(newSentenceDTO.getContent());
                newSentence.setQuestionType(questionTypeRepository
                        .findByType(EQuestionType.valueOf(newSentenceDTO.getQuestionType()))
                        .orElseThrow(() -> new RuntimeException("Question type not found")));

                // Add new answers to the new sentence
                for (AnswerDTO newAnswerDTO : newSentenceDTO.getAnswers()) {
                    Answer newAnswer = new Answer();
                    newAnswer.setAnswerContent(newAnswerDTO.getAnswerContent());
                    newAnswer.setIsCorrect(newAnswerDTO.getIsCorrect());
                    newSentence.addAnswer(newAnswer);
                }

                // Add the new sentence to the worksheet
                worksheet.addSentence(newSentence);
            }
        }
    }



    @Transactional
    public String deleteWorksheet(Integer id) {
        worksheetRepository.deleteById(id);
        return String.format("Worksheet with id %d is deleted", id);
    }

    public List<QuestionType> getAllQuestionTypes() {
        return questionTypeRepository.findAll();
    }

}
