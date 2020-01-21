package by.vlad.springproject1.service;

import by.vlad.springproject1.entity.enums.Person;
import by.vlad.springproject1.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAllPerson();
    void addNewPerson(Person person);
    void deletePerson(Person person );
    void editPerson(Person person, Long id);
    Person getById(long id) throws ResourceNotFoundException;
}