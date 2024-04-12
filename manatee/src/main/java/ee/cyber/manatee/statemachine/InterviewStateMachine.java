package ee.cyber.manatee.statemachine;


import ee.cyber.manatee.model.Interview;
import org.springframework.statemachine.StateMachine;


public interface InterviewStateMachine {
    StateMachine<InterviewState, InterviewEvent> scheduleInterview(Interview interview);
}
