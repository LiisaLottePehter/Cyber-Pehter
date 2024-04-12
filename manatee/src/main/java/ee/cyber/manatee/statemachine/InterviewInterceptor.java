package ee.cyber.manatee.statemachine;


import ee.cyber.manatee.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.transition.Transition;
import org.springframework.messaging.Message;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.stereotype.Component;


import java.util.Optional;


import static ee.cyber.manatee.statemachine.InterviewStateMachineImpl.INTERVIEW_ID_HEADER;


@Slf4j
@Component
@RequiredArgsConstructor
public class InterviewInterceptor extends StateMachineInterceptorAdapter<InterviewState, InterviewEvent> {
    private final InterviewRepository interviewRepository;


    @Override
    public void preStateChange(
            State<InterviewState, InterviewEvent> state,
            Message<InterviewEvent> message,
            Transition<InterviewState, InterviewEvent> transition,
            StateMachine<InterviewState, InterviewEvent> stateMachine
    ){
        Optional.ofNullable(message)
                .flatMap(eventMessage -> Optional.ofNullable(
                        eventMessage.getHeaders()
                                .get(INTERVIEW_ID_HEADER, Integer.class)))
                .flatMap(interviewRepository::findById)
                .ifPresentOrElse(interview -> {
                    interview.setInterviewState(state.getId());


                    interviewRepository.save(interview);
                }, () -> {
                    log.error("Statemachine preStateChange failed! Possible empty message or "
                            + "invalid interview id detected.");
                    throw new IllegalArgumentException("Statemachine preStateChange failed!");
                });
    }
}
