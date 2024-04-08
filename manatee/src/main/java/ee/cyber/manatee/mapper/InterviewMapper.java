package ee.cyber.manatee.mapper;

import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.model.Interview;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterviewMapper {

    InterviewDto entityToDto(Interview interview);

    Interview dtoToEntity(InterviewDto interviewDto);

    List<InterviewDto> entityToDtoList(List<Interview> interviews);
}
