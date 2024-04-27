package org.example.task_version_2.repository;

import jakarta.transaction.Transactional;
import org.example.task_version_2.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findById(Long id);
    List<Person> findByEmail(String email);
    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE person_table AUTO_INCREMENT = 1", nativeQuery = true)
    void resetId();
}
