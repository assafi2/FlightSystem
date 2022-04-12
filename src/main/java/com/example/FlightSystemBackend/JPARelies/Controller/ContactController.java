package com.example.FlightSystemBackend.JPARelies.Controller;

import com.example.FlightSystemBackend.JPARelies.DTO.Contact;
import com.example.FlightSystemBackend.JPARelies.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactus")
public class ContactController {

    @Autowired
    ContactService contactService ;

    @GetMapping("/")
    public List<Contact> getAllHolidays()
    {
        return contactService.getAllContacts() ;
    }

    @GetMapping("/{id}")
    public Contact getHoliday(@PathVariable String id)
    {
        return contactService.getContact(Integer.parseInt(id)) ;
    }

    @PostMapping("/")
    public void addContact(@RequestBody Contact contact){
        contactService.addContact(contact);
    }
}
