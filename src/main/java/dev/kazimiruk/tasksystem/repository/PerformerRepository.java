package dev.kazimiruk.tasksystem.repository;

import dev.kazimiruk.tasksystem.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {
}
