package com.example.FlightSystemBackend.PersistantDomainObjects;

public class Airline_Company implements POCO {


    public long id ;
    public String name ;
    public int country_id;
    public long user_id;

    public Airline_Company() {
    }

    public Airline_Company(long id, String name, int countryId, long userId) {
        this.id = id;
        this.name = name;
        this.country_id = countryId;
        this.user_id = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "AirlineCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + country_id +
                ", userId=" + user_id +
                '}';
    }
    public String toRecordString() {
        return "'%s',%s,%s".formatted(name, country_id, user_id) ;
    }
    public String[] toStringArray() {
        String[] arr = {"'%s'".formatted(name), "%s".formatted(country_id),
                "%s".formatted(user_id),"%s".formatted(id)};
        return arr;
    }
}
