package ee.cyber.manatee.statemachine;

import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.repository.InterviewRepository;
import ee.cyber.manatee.service.InterviewService;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static ee.cyber.manatee.statemachine.InterviewState.SCHEDULED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InterviewStateMachineTests {

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewStateMachine interviewStateMachine;

    @Autowired
    InterviewRepository interviewRepository;

    @Test
    @Transactional
    public void interviewGetsScheduled(){
        val newCandidate = Candidate.builder().firstName("Mari").lastName("Murakas").build();
        val newInterview = Interview
                .builder().candidate(newCandidate)
                .build();

        val saved = interviewService.scheduleInterview(newInterview);
        val initial = saved.getInterviewState();
        assertEquals(SCHEDULED, saved.getInterviewState());
    }
}
