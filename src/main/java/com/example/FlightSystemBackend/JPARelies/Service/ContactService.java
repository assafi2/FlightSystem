package com.example.FlightSystemBackend.JPARelies.Service;


import aj.org.objectweb.asm.ModuleVisitor;

import antlr.collections.impl.LList;
import com.example.FlightSystemBackend.JPARelies.DTO.Contact;
import com.example.FlightSystemBackend.JPARelies.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// to support only GET,POST requests

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRep ;

    public Contact getContact(int id) {
        return contactRep.findById(id).orElse(null) ;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>() ;
        contactRep.findAll().forEach(contactList::add);
        return contactList ;
    }

    public void addContact(Contact contact){

        contactRep.save(contact) ;
    }

    // better run test from REST

    public static void main(String[] args) {

        Contact c = new Contact(1,"yossi","Hey!") ;
        ContactService cs = new ContactService() ;


    }

}



