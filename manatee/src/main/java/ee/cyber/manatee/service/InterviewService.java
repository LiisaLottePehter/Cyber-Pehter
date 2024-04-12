package ee.cyber.manatee.service;

import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.repository.InterviewRepository;
import ee.cyber.manatee.statemachine.InterviewState;
import ee.cyber.manatee.statemachine.InterviewStateMachine;
import ee.cyber.manatee.statemachine.InterviewType;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;

    private final InterviewStateMachine interviewStateMachine;

    public List<Interview> getInterviews() {
        return interviewRepository.findAll();
    }

    public Interview scheduleInterview(Interview interview) {
        interview.setInterviewerName("Karl");
        interview.setId(1);
        interview.setInterviewTime("24.04.2024 12.30.00");
        interview.setInterviewType(InterviewType.BEHAVIOURAL);
        interviewStateMachine.scheduleInterview(interview);
        interview.setInterviewState(InterviewState.SCHEDULED);
        return interviewRepository.save(interview);
    }
    public Interview getInterview(Integer id) {
        Interview interview = interviewRepository.findById(id).orElse(null);
        return interview;
    }
}