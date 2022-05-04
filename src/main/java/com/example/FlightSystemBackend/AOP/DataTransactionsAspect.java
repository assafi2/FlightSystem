package com.example.FlightSystemBackend.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Aspect
public class DataTransactionsAspect {

    //TODO autowired mongo crud repository object for relevant document named "addRepo"


    // document DAO add operations within the system (as well as add operations via JPA) , in a MongoDB document

    @Before("execution(* com.example.FlightSystemBackend.DAO.*.add*(..))")
    // in case it possible add another execution path for JPA data access,
    // otherwise TODO implement a second relevant @before method
    public void allAddMethodsDBUpdate(){
        LocalDateTime currentStamp = LocalDateTime.now() ;
        // TODO create new mongo document DTO object based on curerntStamp value
        // TODO add record to MongoDB with addRepo API's add operation .
    }
}
