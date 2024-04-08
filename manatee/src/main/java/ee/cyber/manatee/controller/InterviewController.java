package ee.cyber.manatee.controller;

import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.mapper.InterviewMapper;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;
    private final InterviewMapper interviewMapper;

    public ResponseEntity<List<InterviewDto>> getAllInterviews() {
        val interviews = interviewService.getInterviews();
        return ResponseEntity.ok(interviewMapper.entityToDtoList(interviews));
    }


    public ResponseEntity<InterviewDto> scheduleInterview(InterviewDto interviewDto) {
        val draftInterview = interviewMapper.dtoToEntity(interviewDto);
        val interview = interviewService.scheduleInterview(draftInterview);

        return ResponseEntity.status(CREATED).body(interviewMapper.entityToDto(interview));
    }
}

