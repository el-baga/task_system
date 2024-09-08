package dev.kazimiruk.tasksystem.service;

import dev.kazimiruk.tasksystem.dto.response.PersonResponse;
import dev.kazimiruk.tasksystem.entity.Person;
import dev.kazimiruk.tasksystem.mapper.PersonMapper;
import dev.kazimiruk.tasksystem.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public List<PersonResponse> getAllPersons() {
        List<PersonResponse> listPersonResponse = new ArrayList<>();
        List<Person> persons = personRepository.findAll();
        persons.forEach(person -> listPersonResponse.add(PersonMapper.INSTANCE.toDto(person)));
        return listPersonResponse;
    }
}
