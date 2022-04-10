package com.example.FlightSystemBackend.BussinessServices;

import java.util.Random;

public class LoginToken {


    enum Role {
        CUSTOMER,AIRLINE,ADMIN
    }
    long id ;
    String name ;
    Role role ;

    public LoginToken(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public boolean equals (LoginToken loginToken) {
        if ((this.id == loginToken.id) && (this.name.equals(loginToken.name))
                && (this.role == loginToken.role)) return true ;
        return false ;
    }

    @Override
    public String toString() {
        return "LoginToken{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}