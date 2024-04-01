package com.darmokhval.Backend_part.mapper;

import com.darmokhval.Backend_part.model.dto.AnswerDTO;
import com.darmokhval.Backend_part.model.dto.Authentication.response.UserDetailsResponseDTO;
import com.darmokhval.Backend_part.model.dto.SentenceDTO;
import com.darmokhval.Backend_part.model.dto.WorksheetDTO;
import com.darmokhval.Backend_part.model.entity.User;
import com.darmokhval.Backend_part.model.entity.tests.*;
import com.darmokhval.Backend_part.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MainMapper {
    private final QuestionTypeRepository questionTypeRepository;

    public UserDetailsResponseDTO convertUserToDTO(User user) {
        UserDetailsResponseDTO userDTO = new UserDetailsResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setUsername(userDTO.getUsername());
        userDTO.setRole(user.getRole().getRole());
        return userDTO;
    }
    public WorksheetDTO convertWorksheetToDTO(Worksheet worksheet) {
        WorksheetDTO worksheetDTO = new WorksheetDTO();
        worksheetDTO.setWorksheetId(worksheet.getWorksheetId());
        worksheetDTO.setTitle(worksheet.getTitle());
        worksheetDTO.setClassYear(worksheet.getClassYear());
        worksheetDTO.setWorksheetId(worksheet.getWorksheetId());
        worksheetDTO.setInstruction(worksheet.getInstruction());
        worksheetDTO.setSubject(worksheet.getSubject());
        List<SentenceDTO> sentenceDTOList = new ArrayList<>();
        for (Sentence sentence : worksheet.getSentences()) {
            SentenceDTO sentenceDTO = convertSentenceToDTO(sentence);
            sentenceDTOList.add(sentenceDTO);
        }
        worksheetDTO.setSentences(sentenceDTOList);
        return worksheetDTO;
    }

    public Worksheet convertWorksheetDTOToEntity(WorksheetDTO worksheetDTO) {
        Worksheet worksheet = new Worksheet();
        worksheet.setTitle(worksheetDTO.getTitle());
        worksheet.setClassYear(worksheetDTO.getClassYear());
        worksheet.setInstruction(worksheetDTO.getInstruction());
        worksheet.setSubject(worksheetDTO.getSubject());

        for (SentenceDTO sentenceDTO : worksheetDTO.getSentences()) {
            Sentence sentence = convertSentenceDTOToEntity(sentenceDTO);
            worksheet.addSentence(sentence);
        }
        return worksheet;
    }

    private SentenceDTO convertSentenceToDTO(Sentence sentence) {
        SentenceDTO sentenceDTO = new SentenceDTO();
        sentenceDTO.setContent(sentence.getContent());
        sentenceDTO.setSentenceId(sentence.getSentenceId());
        sentenceDTO.setWorksheetId(sentence.getWorksheet().getWorksheetId());
        Optional<QuestionType> questionType = Optional.ofNullable(sentence.getQuestionType());
        questionType.ifPresentOrElse(
                qType -> sentenceDTO.setQuestionType(qType.getType().getQuestionType()),
                () -> {
                    sentenceDTO.setQuestionType("FILL_BLANK");
                }
        );

        List<AnswerDTO> answerDTOS = sentence.getAnswers().stream()
                .map(this::convertAnswerToDTO)
                .toList();
        sentenceDTO.setAnswers(answerDTOS);
        return sentenceDTO;
    }

    private Sentence convertSentenceDTOToEntity(SentenceDTO sentenceDTO) {
        Sentence sentence = new Sentence();
        sentence.setContent(sentenceDTO.getContent());
        sentence.setQuestionType(questionTypeRepository
                .findByType(EQuestionType.valueOf(sentenceDTO.getQuestionType()))
                .orElseThrow(() -> new RuntimeException("Question type was not found in database")));
        for(AnswerDTO answerDTO: sentenceDTO.getAnswers()) {
            Answer answer = convertAnswerDTOToEntity(answerDTO);
            sentence.addAnswer(answer);
        }
        return sentence;
    }

    private AnswerDTO convertAnswerToDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswerId(answer.getAnswerId());
        answerDTO.setAnswerContent(answer.getAnswerContent());
        answerDTO.setSentenceId(answer.getSentence().getSentenceId());
        answerDTO.setIsCorrect(answer.getIsCorrect());
        return answerDTO;
    }

    private Answer convertAnswerDTOToEntity(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setAnswerContent(answerDTO.getAnswerContent());
        answer.setIsCorrect(answerDTO.getIsCorrect());
        return answer;
    }


}
