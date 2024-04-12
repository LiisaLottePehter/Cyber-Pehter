package ee.cyber.manatee.model;

import ee.cyber.manatee.statemachine.InterviewState;
import ee.cyber.manatee.statemachine.InterviewType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String interviewerName;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Candidate candidate;

    @NotNull
    private String interviewTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InterviewState interviewState;
}
