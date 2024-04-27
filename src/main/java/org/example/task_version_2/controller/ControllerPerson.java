package org.example.task_version_2.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.task_version_2.entity.Person;
import org.example.task_version_2.exception.ApiRequestException;
import org.example.task_version_2.service.ServicePerson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ControllerPerson {

    private final ServicePerson servicePerson;

    @GetMapping
    List<Person> getAll() {

        return servicePerson.getAll();
    }

    @PostMapping
    public ResponseEntity<String> savePerson(@RequestBody Person person) {
        servicePerson.savePerson(person);
        String message = "Person " + person.getName() + " was added to Database";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        servicePerson.deletePerson(id);
        String message = "Person with id " + id + " deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String email
    ) {
        servicePerson.updatePerson(id, name, email);
        String message = "Person with id " + id + " updated";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllData() {
        servicePerson.deleteAllData();
        String message = "All persons was delete from DB";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
