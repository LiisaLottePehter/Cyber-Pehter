package ee.cyber.manatee.statemachine;


import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.repository.InterviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import org.springframework.messaging.support.MessageBuilder;


@Slf4j
@Component
@RequiredArgsConstructor
public class InterviewStateMachineImpl implements InterviewStateMachine {
    public static final String INTERVIEW_ID_HEADER = "interview_id";

    private final InterviewRepository interviewRepository;

    @Autowired
    @Qualifier(value = "factory2")
    private final StateMachineFactory<InterviewState, InterviewEvent> stateMachineFactory;


    @Override
    @Transactional
    public StateMachine<InterviewState, InterviewEvent> scheduleInterview(
            Interview interview
    ){
        StateMachine<InterviewState, InterviewEvent> stateMachine = build(interview.getId());
        sendEvent(interview.getId(), stateMachine, InterviewEvent.SCHEDULE);


        return stateMachine;
    }


    private void sendEvent(Integer id,
                           StateMachine<InterviewState, InterviewEvent> stateMachine,
                           InterviewEvent interviewEvent) {
        Message message = MessageBuilder
                .withPayload(interviewEvent)
                .setHeader(INTERVIEW_ID_HEADER, id)
                .build();
        stateMachine.sendEvent(message);
    }


    private StateMachine<InterviewState, InterviewEvent> build(Integer id) {
        Interview interview = interviewRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.error("Couldn't find the interview with given id");
                    throw new IllegalArgumentException("Invalid interview id");
                });
        StateMachine<InterviewState, InterviewEvent> stateMachine = stateMachineFactory.getStateMachine();


        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachine(new DefaultStateMachineContext<>(
                            interview.getInterviewState(), null, null, null
                    ));
                });
        stateMachine.start();
        return stateMachine;
    }
}
