package com.darmokhval.Backend_part.model.entity.tests;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "worksheets")
public class Worksheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer worksheetId;

    @Column(name = "title")
    @NotEmpty(message = "field `title` should not be empty")
    @Size(min = 1, max = 127, message = "field should contain minimum - 1, maximum - 127 characters")
    private String title;

    @Column(name = "test_instruction")
    @NotEmpty(message = "field `testInstruction` should not be empty")
    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
    private String instruction;

    @NotEmpty(message = "field `grade` should not be empty")
    @Size(min = 1, max = 12)
    private String classYear;

    @Column(name = "subject")
    @NotEmpty(message = "field `subject` should not be empty")
    @Size(min = 1, max = 50, message = "field should contain minimum - 1, maximum - 50 characters")
    private String subject;

    @OneToMany(mappedBy = "worksheet")
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "worksheet", orphanRemoval = true)
    private List<Sentence> sentences = new ArrayList<>();

    public Worksheet() {
    }

    public Integer getWorksheetId() {
        return worksheetId;
    }

    public String getTitle() {
        return title;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getClassYear() {
        return classYear;
    }

    public String getSubject() {
        return subject;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setWorksheetId(Integer worksheetId) {
        this.worksheetId = worksheetId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void addSentence(Sentence sentence) {
        this.sentences.add(sentence);
        sentence.setWorksheet(this);
    }

    public void removeSentence(Integer id) {
        Optional<Sentence> sentenceOptional = this.sentences.stream()
                .filter(sentence -> sentence.getSentenceId().equals(id))
                .findFirst();
        if (sentenceOptional.isPresent()) {
            this.sentences.remove(sentenceOptional.get());
            sentenceOptional.get().setWorksheet(null);
        }
    }

    //TODO change from sentEmail & clientRequest to Worksheet & Sentences, and check for Answers too. This should work better.

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worksheet worksheet = (Worksheet) o;

        if (!worksheetId.equals(worksheet.worksheetId)) return false;
        if (!title.equals(worksheet.title)) return false;
        if (!instruction.equals(worksheet.instruction)) return false;
        if (!classYear.equals(worksheet.classYear)) return false;
        if (!subject.equals(worksheet.subject)) return false;
        if (!Objects.equals(ratings, worksheet.ratings)) return false;
        return sentences.equals(worksheet.sentences);
    }

    @Override
    public int hashCode() {
        int result = worksheetId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + instruction.hashCode();
        result = 31 * result + classYear.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + (ratings != null ? ratings.hashCode() : 0);
        result = 31 * result + sentences.hashCode();
        return result;
    }
}
