package org.example.task_version_2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person_table")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirthday;
    @Transient
    private int age;

    public Person(String name, String email, LocalDate dateOfBirthday, int age) {
        this.name = name;
        this.email = email;
        this.dateOfBirthday = dateOfBirthday;
        this.age = age;
    }

    public int getAge() {
        return Period.between(this.dateOfBirthday, LocalDate.now()).getYears();
    }
}
