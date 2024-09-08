package dev.kazimiruk.tasksystem.repository;

import dev.kazimiruk.tasksystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String email);

}
