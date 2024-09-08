package dev.kazimiruk.tasksystem.mapper;

import dev.kazimiruk.tasksystem.dto.request.RegisterRequest;
import dev.kazimiruk.tasksystem.dto.response.PersonResponse;
import dev.kazimiruk.tasksystem.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    PersonMapper INSTANCE = getMapper(PersonMapper.class);

    Person toEntity(RegisterRequest dto);

    PersonResponse toDto(Person entity);
}
