package com.devsu.customerservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsu.common.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * General query to get all persons
     * @return List of persons
     */
    @Query(value = "SELECT * FROM person", nativeQuery = true)
    List<Person> getPersons();
}
