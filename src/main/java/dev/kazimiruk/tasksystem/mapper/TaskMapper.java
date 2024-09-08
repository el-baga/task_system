package dev.kazimiruk.tasksystem.mapper;

import dev.kazimiruk.tasksystem.dto.request.TaskRequest;
import dev.kazimiruk.tasksystem.dto.response.TaskResponse;
import dev.kazimiruk.tasksystem.entity.Task;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskMapper INSTANCE = getMapper(TaskMapper.class);

    Task toEntity(TaskRequest dto);

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "performer", source = "entity.performer")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "author", source = "entity.author")
    Task toEntity(TaskRequest dto, Task entity);

    TaskResponse toDto(Task entity);
}
