package com.darmokhval.Backend_part.model.entity.tests;

import com.darmokhval.Backend_part.model.dto.SentenceDTO;
import com.darmokhval.Backend_part.model.dto.WorksheetDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worksheets")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "class")
    @NotEmpty(message = "field `grade` should not be empty")
    @Size(min = 1, max = 12)
    private String classYear;

    @Column(name = "subject")
    @NotEmpty(message = "field `subject` should not be empty")
    @Size(min = 1, max = 50, message = "field should contain minimum - 1, maximum - 50 characters")
    private String subject;

    @OneToMany(mappedBy = "worksheet")
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "worksheet")
    private List<Sentence> sentences;
}