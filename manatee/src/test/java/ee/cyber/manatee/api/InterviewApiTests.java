package ee.cyber.manatee.api;

import ee.cyber.manatee.dto.CandidateDto;
import ee.cyber.manatee.dto.InterviewDto;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InterviewApiTests {

    @Autowired
    private InterviewApi interviewApi;

    @Test
    public void scheduleInterview() {
        val draftCandidate = CandidateDto
                .builder().firstName("Mari").lastName("Maasikas").build();
        val draftInterview = InterviewDto
                .builder().candidate(draftCandidate)
                .id(5)
                .interviewerName("Karl")
                .interviewTime("24.04.2024 12.30.00")
                .interviewType(InterviewDto.InterviewTypeEnum.BEHAVIOURAL)
                .interviewState(InterviewDto.InterviewStateEnum.SCHEDULED)
                .build();

        val response = interviewApi.scheduleInterview(draftInterview);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        val interview = response.getBody();
        assertNotNull(interview);
        assertNotNull(interview.getId());
        assertNotNull(interview.getInterviewerName());
        assertNotNull(interview.getInterviewTime());
        assertNotNull(interview.getInterviewType());
        assertNotNull(interview.getInterviewState());

        assertEquals(draftInterview.getCandidate().getFirstName(), interview.getCandidate().getFirstName());
        assertEquals(draftInterview.getCandidate().getLastName(), interview.getCandidate().getLastName());
        assertEquals(draftInterview.getInterviewTime(), interview.getInterviewTime());
        assertEquals(draftInterview.getInterviewerName(), interview.getInterviewerName());
        assertEquals(draftInterview.getInterviewType(), interview.getInterviewType());
    }

}
