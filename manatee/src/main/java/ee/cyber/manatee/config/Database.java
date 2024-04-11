package ee.cyber.manatee.config;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.repository.ApplicationRepository;
import ee.cyber.manatee.statemachine.ApplicationState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.time.OffsetDateTime;
import java.util.List;

import static ee.cyber.manatee.statemachine.InterviewType.BEHAVIOURAL;
import static ee.cyber.manatee.statemachine.InterviewType.INFORMAL;

@Configuration
public class Database {

    @Bean
    CommandLineRunner commandLineRunner(ApplicationRepository applicationRepository) {
        return args -> {
            Candidate candidate1 = new Candidate(1, "Mari", "Maasikas");
            Candidate candidate2 = new Candidate(2, "Karl", "Karu");
            Candidate candidate3 = new Candidate(3, "Karoliine", "Kohver");
            Candidate candidate4 = new Candidate(4, "Markus", "Maasikas");
            Interview interview1 = new Interview(1, "Karl", candidate1, "24.03.2024 12.30.00", INFORMAL);
            Interview interview2 = new Interview(2, "Maria", candidate3, "24.03.2024 14.30.00", BEHAVIOURAL);
            Interview interview3 = new Interview(3, "Karl", candidate4, "24.03.2024 13.30.00", BEHAVIOURAL);

            Application application = new Application(1, ApplicationState.INTERVIEW, candidate1, OffsetDateTime.now(), interview1);
            Application application2 = new Application(2, ApplicationState.REJECTED, candidate2, OffsetDateTime.now(), null);
            Application application3 = new Application(3, ApplicationState.INTERVIEW, candidate3, OffsetDateTime.now(), interview2);
            Application applidation4 = new Application(4, ApplicationState.INTERVIEW, candidate4, OffsetDateTime.now(), interview3);

            applicationRepository.saveAll(List.of(application, application2, application3, applidation4));
        };
    }

}
