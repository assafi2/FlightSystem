package com.example.FlightSystemBackend.JPARelies.Repository;

import com.example.FlightSystemBackend.JPARelies.DTO.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact,Integer> {

}
