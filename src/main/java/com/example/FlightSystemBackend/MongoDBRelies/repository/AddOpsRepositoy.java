package com.example.FlightSystemBackend.MongoDBRelies.repository;

import com.example.FlightSystemBackend.MongoDBRelies.DTO.AddOp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddOpsRepositoy extends MongoRepository<AddOp,Integer> {

}
