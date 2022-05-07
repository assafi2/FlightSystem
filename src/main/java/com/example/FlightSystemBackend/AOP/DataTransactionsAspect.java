package com.example.FlightSystemBackend.AOP;

import com.example.FlightSystemBackend.MongoDBRelies.DTO.AddOp;
import com.example.FlightSystemBackend.MongoDBRelies.repository.AddOpsRepositoy;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Aspect
public class DataTransactionsAspect {


    @Autowired
    AddOpsRepositoy addOpsRepo ;

    // document DAO add operations within the system (as well as add operations via JPA) , in a MongoDB document

    @Before("execution(* com.example.FlightSystemBackend.DAO.*.add*(..))")
    // in case it possible add another execution path for JPA data access,
    // otherwise TODO implement a second relevant @before method
    public void allAddMethodsDBUpdate(){

        int nextId = (int)addOpsRepo.count();  // simulated auto increment mechanism
        addOpsRepo.insert(new AddOp(nextId,LocalDateTime.now())) ;
    }
}
