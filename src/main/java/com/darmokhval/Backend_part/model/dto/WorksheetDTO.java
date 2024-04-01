package com.darmokhval.Backend_part.model.dto;

import com.darmokhval.Backend_part.model.entity.tests.Sentence;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorksheetDTO {
    private Integer worksheetId;
    private String title;
    private String classYear;
    private String instruction;
    private String subject;
    private List<SentenceDTO> sentences = new ArrayList<>();

    public void addSentenceDTO(SentenceDTO sentenceDTO) {
        this.sentences.add(sentenceDTO);
    }

    public void removeSentenceDTO(Integer id) {
        this.sentences.removeIf(sentenceDTO -> sentenceDTO.getSentenceId().equals(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorksheetDTO that = (WorksheetDTO) o;

        if (!worksheetId.equals(that.worksheetId)) return false;
        if (!title.equals(that.title)) return false;
        if (!classYear.equals(that.classYear)) return false;
        if (!instruction.equals(that.instruction)) return false;
        if (!subject.equals(that.subject)) return false;
        return sentences.equals(that.sentences);
    }

    @Override
    public int hashCode() {
        int result = worksheetId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + classYear.hashCode();
        result = 31 * result + instruction.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + sentences.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WorksheetDTO{" +
                "worksheetId=" + worksheetId +
                ", title='" + title + '\'' +
                ", classYear='" + classYear + '\'' +
                ", instruction='" + instruction + '\'' +
                ", subject='" + subject + '\'' +
                ", sentences=" + sentences +
                '}';
    }
}
