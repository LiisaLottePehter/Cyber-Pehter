package ee.cyber.manatee.config;

import ee.cyber.manatee.statemachine.InterviewEvent;
import ee.cyber.manatee.statemachine.InterviewState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "factory2")
public class InterviewConfig extends StateMachineConfigurerAdapter<InterviewState, InterviewEvent> {

    public void configure(StateMachineStateConfigurer<InterviewState, InterviewEvent> states) throws Exception {
        states.withStates()
                .initial(InterviewState.SCHEDULE)
                .states(EnumSet.allOf(InterviewState.class))
                .end(InterviewState.FINISHED)
                .end(InterviewState.CANCELLED);
    }

    public void configure(StateMachineTransitionConfigurer<InterviewState, InterviewEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(InterviewState.SCHEDULE)
                .target(InterviewState.FINISHED)
                .event(InterviewEvent.FINISH)

                .and()
                .withExternal()
                .source(InterviewState.SCHEDULE)
                .target(InterviewState.CANCELLED)
                .event(InterviewEvent.CANCEL)

                .and()
                .withExternal()
                .source(InterviewState.SCHEDULE)
                .target(InterviewState.SCHEDULED)
                .event(InterviewEvent.SCHEDULE);
    }
}
