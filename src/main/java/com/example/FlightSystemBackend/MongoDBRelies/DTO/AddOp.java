package com.example.FlightSystemBackend.MongoDBRelies.DTO;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
public class AddOp {
//  should be auto incremented
    @Id
    public int id ;
    public LocalDateTime opTime ;

    public AddOp() {
    }

    public AddOp(int id, LocalDateTime opTime) {
        this.id = id;
        this.opTime = opTime;
    }
}
