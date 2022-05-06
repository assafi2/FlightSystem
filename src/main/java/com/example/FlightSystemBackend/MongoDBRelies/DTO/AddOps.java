package com.example.FlightSystemBackend.MongoDBRelies.DTO;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
public class AddOps {

    @Id
    int id ;
    LocalDateTime opTime ;

    public AddOps() {
    }

    public AddOps(int id, LocalDateTime opTime) {
        this.id = id;
        this.opTime = opTime;
    }
}
