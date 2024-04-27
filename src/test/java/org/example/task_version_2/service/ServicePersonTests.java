package org.example.task_version_2.service;

import org.example.task_version_2.entity.Person;
import org.example.task_version_2.exception.ApiRequestException;
import org.example.task_version_2.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ServicePersonTests {
    @InjectMocks
    private ServicePerson servicePerson;
    @Mock
    private PersonRepository personRepository;

    @Test
    void getAll() {
        List<Person> personList = Arrays.asList(
                new Person("Jan", "jan.student@gmail.com", LocalDate.of(1998, 8, 9), 25),
                new Person("Anna", "anna.smith@example.com", LocalDate.of(1990, 5, 15), 32)
        );
        Mockito.when(personRepository.findAll()).thenReturn(personList);

        List<Person> secondPersonList = servicePerson.getAll();

        Assertions.assertEquals(personList.size(), secondPersonList.size());
        Assertions.assertEquals(personList, secondPersonList);
    }

    @Test
    void savePerson() {
        //given
        Person person = new Person("Alla", "alla@gmail.com", LocalDate.of(1990, 10, 15), 31);
        Mockito.when(personRepository.findByEmail(person.getEmail())).thenReturn(Collections.emptyList());
        Mockito.when(personRepository.save(person)).thenReturn(person);

        Person savedPerson = servicePerson.savePerson(person);

        Assertions.assertNotNull(savedPerson);
        Assertions.assertEquals(person, savedPerson);

        Mockito.when(personRepository.findByEmail(person.getEmail())).thenReturn(Collections.singletonList(person));
        //then
        Assertions.assertThrows(ApiRequestException.class, () -> {
            servicePerson.savePerson(person);
        });

    }

    @Test
    void deletePerson() {
        Long myId = 1L;
        Person person = new Person("Jan", "jan@gmail.com", LocalDate.of(1990, 10, 15), 31);

        Mockito.when(personRepository.findById(myId)).thenReturn(Optional.of(person));
        servicePerson.deletePerson(myId);
        Mockito.verify(personRepository, Mockito.times(1)).deleteById(myId);
    }
}
