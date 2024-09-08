package dev.kazimiruk.tasksystem.service;

import dev.kazimiruk.tasksystem.dto.request.TaskRequest;
import dev.kazimiruk.tasksystem.dto.response.TaskResponse;
import dev.kazimiruk.tasksystem.entity.Performer;
import dev.kazimiruk.tasksystem.entity.Person;
import dev.kazimiruk.tasksystem.entity.Task;
import dev.kazimiruk.tasksystem.exception.BadRequestException;
import dev.kazimiruk.tasksystem.mapper.PersonMapper;
import dev.kazimiruk.tasksystem.mapper.TaskMapper;
import dev.kazimiruk.tasksystem.repository.PerformerRepository;
import dev.kazimiruk.tasksystem.repository.PersonRepository;
import dev.kazimiruk.tasksystem.repository.TaskRepository;
import dev.kazimiruk.tasksystem.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    private final PersonRepository personRepository;

    private final PerformerRepository performerRepository;

    public TaskResponse createTask(TaskRequest taskRequest) {
        if(taskRepository.existsByTitle(taskRequest.getTitle())) {
            throw new BadRequestException("Задача с заголовком '" + taskRequest.getTitle() + "' уже существует");
        }

        Long personId = CommonUtil.getCurrentUserId();
        Person person = getPersonById(personId);
        Task task = TaskMapper.INSTANCE.toEntity(taskRequest);
        task.setAuthor(person);
        taskRepository.saveAndFlush(task);
        log.info("{} создал задачу '{}'", person.getFirstName(), task.getTitle());
        return getTaskResponse(task);
    }

    public TaskResponse editTask(Long id, TaskRequest taskRequest) {
        Long personId = CommonUtil.getCurrentUserId();
        Person person = getPersonById(personId);
        Task task = getTaskById(id);
        Task editedTask = TaskMapper.INSTANCE.toEntity(taskRequest, task);
        taskRepository.save(editedTask);
        log.info("{} обновил задачу '{}'", person.getFirstName(), task.getTitle());
        return getTaskResponse(task);
    }

    public Timestamp deleteTask(Long id) {
        Long personId = CommonUtil.getCurrentUserId();
        Person person = getPersonById(personId);
        Task task = getTaskById(id);
        taskRepository.delete(task);
        log.info("{} удалил задачу '{}'", person.getFirstName(), task.getTitle());
        return Timestamp.from(Instant.now());
    }

    public List<TaskResponse> getTasks(int page, int size) {
        Long personId = CommonUtil.getCurrentUserId();
        Person person = getPersonById(personId);
        List<TaskResponse> listTaskResponse = getListTaskResponse(person);
        int start = page * size;
        int end = Math.min(start + size, listTaskResponse.size());
        return start >= listTaskResponse.size() ?
                Collections.emptyList() : getListTaskResponse(person).subList(start, end);
    }

    private List<TaskResponse> getListTaskResponse(Person person) {
        List<TaskResponse> listTaskResponse = new ArrayList<>();
        List<Task> authorTasks = person.getAuthorTasks();
        List<Performer> performerTasks = person.getPerformers();
        authorTasks.forEach(authorTask -> listTaskResponse.add(getTaskResponse(authorTask)));
        performerTasks.forEach(performerTask -> listTaskResponse.add(getTaskResponse(performerTask.getTask())));
        return listTaskResponse;
    }

    @Transactional
    public TaskResponse setTaskPerformer(Long taskId, Long performerId) {
        Task task = getTaskById(taskId);
        Person person = getPersonById(performerId);
        Performer performer = new Performer();
        performer.setPerson(person);
        performer.setTask(task);
        performerRepository.save(performer);
        task.setPerformer(performer);
        taskRepository.save(task);
        log.info("В качестве исполнителя задачи '{}' был установлен пользователь '{}'", task.getTitle(), person.getFirstName());
        return getTaskResponse(task);
    }

    private TaskResponse getTaskResponse(Task task) {
        TaskResponse taskResponse = TaskMapper.INSTANCE.toDto(task);
        taskResponse.setAuthor(PersonMapper.INSTANCE.toDto(task.getAuthor()));
        Optional<Performer> performer = Optional.ofNullable(task.getPerformer());
        taskResponse.setPerformer(performer.map(value -> PersonMapper.INSTANCE.toDto(value.getPerson())).orElse(null));
        return taskResponse;
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new BadRequestException("Пользователь с таким id (" + id + ") не существует"));
    }

    public Person getPersonByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Указанная почта не зарегистрирована в системе"));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new BadRequestException("Задача с таким id (" + id + ") не существует"));
    }
}
