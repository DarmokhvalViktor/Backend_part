package com.darmokhval.Backend_part.model.entity.tests;

import com.darmokhval.Backend_part.model.dto.AnswerDTO;
import com.darmokhval.Backend_part.model.dto.SentenceDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "sentences")
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sentenceId;

    @Column(name = "content")
    @NotEmpty(message = "field `taskSentence` should not be empty")
    @Size(min = 10, max = 300, message = "field should contain minimum - 10, maximum - 300 characters")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sentence", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worksheet_id")
    private Worksheet worksheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_type_id")
    private QuestionType questionType;

    public Sentence() {
    }

    public Integer getSentenceId() {
        return sentenceId;
    }

    public String getContent() {
        return content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setSentenceId(Integer sentenceId) {
        this.sentenceId = sentenceId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setWorksheet(Worksheet worksheet) {
        this.worksheet = worksheet;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setSentence(this);
    }

    public void removeAnswer(Integer id) {
        Optional<Answer> answerOptional = this.answers.stream()
                .filter(answer -> answer.getAnswerId().equals(id))
                .findFirst();
        if(answerOptional.isPresent()) {
            this.answers.remove(answerOptional.get());
            answerOptional.get().setSentence(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        if (!sentenceId.equals(sentence.sentenceId)) return false;
        if (!content.equals(sentence.content)) return false;
        if (!answers.equals(sentence.answers)) return false;
        if (!worksheet.equals(sentence.worksheet)) return false;
        return questionType.equals(sentence.questionType);
    }

    @Override
    public int hashCode() {
        int result = sentenceId.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + answers.hashCode();
        result = 31 * result + worksheet.hashCode();
        result = 31 * result + questionType.hashCode();
        return result;
    }
}
//public List<SentEmail> getSentEmails() {
//    return sentEmails;
//}
//
//public void addSentEmail(SentEmail sentEmail) {
//    this.sentEmails.add(sentEmail);
//    sentEmail.setClientRequest(this);
//}
//
//public void removeSentEmail(Integer id) {
//    Optional<SentEmail> sentEmailOptional = this.sentEmails.stream()
//            .filter(sentEmail -> sentEmail.getId().equals(id))
//            .findFirst();
//    if (sentEmailOptional.isPresent()) {
//        this.sentEmails.remove(sentEmailOptional.get());
//        sentEmailOptional.get().setClientRequest(null);
//    }
//}
//
//@OneToMany(mappedBy = "clientRequest", cascade = CascadeType.ALL, orphanRemoval = true)
//private List<SentEmail> sentEmails = new ArrayList<>();
//
//@ManyToOne(fetch = FetchType.LAZY, optional = false)
//@JoinColumn(name = "client_request_id")
//private ClientRequest clientRequest;
