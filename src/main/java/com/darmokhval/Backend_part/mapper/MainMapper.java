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
import java.util.stream.Collectors;

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
        WorksheetDTO testContainerDTO = new WorksheetDTO();
        testContainerDTO.setTitle(worksheet.getTitle());
        testContainerDTO.setClassYear(worksheet.getClassYear());
        testContainerDTO.setWorksheetId(worksheet.getWorksheetId());
        testContainerDTO.setInstruction(worksheet.getInstruction());
        testContainerDTO.setSubject(worksheet.getSubject());
        List<SentenceDTO> sentenceDTOList = new ArrayList<>();
        for (Sentence sentence : worksheet.getSentences()) {
            SentenceDTO sentenceDTO = convertSentenceToDTO(sentence);
            sentenceDTOList.add(sentenceDTO);
        }
        testContainerDTO.setSentences(sentenceDTOList);
        return testContainerDTO;
    }

    public Worksheet convertWorksheetDTOToEntity(WorksheetDTO testContainerDTO) {
        Worksheet worksheet = new Worksheet();
        worksheet.setTitle(testContainerDTO.getTitle());
        worksheet.setClassYear(testContainerDTO.getClassYear());
        worksheet.setInstruction(testContainerDTO.getInstruction());
        worksheet.setSubject(testContainerDTO.getSubject());
        List<Sentence> sentences = new ArrayList<>();

        for (SentenceDTO test : testContainerDTO.getSentences()) {
            Sentence sentence2 = convertSentenceDTOToEntity(test);
            sentence2.setWorksheet(worksheet);
            sentences.add(sentence2);
        }
        worksheet.setSentences(sentences);
        return worksheet;
    }

    private SentenceDTO convertSentenceToDTO(Sentence sentence) {
        SentenceDTO sentenceDTO = new SentenceDTO();
        sentenceDTO.setContent(sentence.getContent());
        sentenceDTO.setSentenceId(sentence.getSentenceId());
        Optional<QuestionType> questionType = Optional.ofNullable(sentence.getQuestionType());
        questionType.ifPresentOrElse(
                qType -> sentenceDTO.setQuestionTypeId(qType.getId()),
                () -> {
                    sentenceDTO.setQuestionTypeId(3L);
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
        Optional<Long> questionTypeId = Optional.ofNullable(sentenceDTO.getQuestionTypeId());
        if (questionTypeId.isPresent()) {
            Optional<QuestionType> optionalQuestionType = questionTypeRepository.findById(questionTypeId.get());

            optionalQuestionType.ifPresentOrElse(
                    sentence::setQuestionType,
                    () -> {
                        sentence.setQuestionType(questionTypeRepository.findByType(EQuestionType.FILL_BLANK)
                                .orElseThrow(() -> new IllegalStateException("FILL_BLANK question type not found")));
                    }
            );
        } else {
            sentence.setQuestionType(questionTypeRepository.findByType(EQuestionType.FILL_BLANK)
                    .orElseThrow(() -> new IllegalStateException("FILL_BLANK question type not found")));
        }

        List<Answer> answerList = sentenceDTO.getAnswers().stream()
                .map(answerDTO -> {
                    Answer answer = convertAnswerDTOToEntity(answerDTO);
                    answer.setSentence(sentence);
                    return answer;
                })
                .toList();
        sentence.setAnswers(answerList);

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
