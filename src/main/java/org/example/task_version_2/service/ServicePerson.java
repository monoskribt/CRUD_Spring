package org.example.task_version_2.service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.task_version_2.entity.Person;
import org.example.task_version_2.exception.ApiRequestException;
import org.example.task_version_2.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicePerson {
    PersonRepository personRepository;

    public List<Person> getAll() {
        List<Person> personList = personRepository.findAll();
        if(personList.isEmpty()) {
            throw new ApiRequestException("List person is empty", HttpStatus.NOT_FOUND);
        }
        return personList;
    }

    public Person savePerson(Person person) {
        List<Person> listPerson = personRepository.findByEmail(person.getEmail());
        if (!listPerson.isEmpty()) {
            throw new ApiRequestException("This is email already exist", HttpStatus.BAD_REQUEST);
        }
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        Optional<Person> listPerson = personRepository.findById(id);
        if (listPerson.isEmpty()) {
            throw new ApiRequestException("There is no employee with this Id", HttpStatus.NOT_FOUND);
        }
        personRepository.deleteById(id);
    }

    @Transactional
    public void updatePerson(Long id, String name, String email) {
        Person person = personRepository.findById(id).
                orElseThrow(() -> new ApiRequestException("There is no employee with this Id", HttpStatus.NOT_FOUND));

        if (name != null && !name.isEmpty() && !name.equals(person.getName())) {
            person.setName(name);
        }

        if (email != null && !email.isEmpty() && !email.equals(person.getEmail())) {
            List<Person> listPerson = personRepository.findByEmail(email);
            if (!listPerson.isEmpty()) {
                throw new ApiRequestException("This is email already exist", HttpStatus.BAD_REQUEST);
            }
            person.setEmail(email);
        }
        personRepository.save(person);
    }

    @Transactional
    public void deleteAllData() {
        personRepository.deleteAll();
        personRepository.resetId();
    }
}
