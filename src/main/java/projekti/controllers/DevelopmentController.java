/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.services.ContactsService;

/**
 *  Just some endpoints to manipulate data
 *  while developing
 */
@Controller
@Profile({"dev"})
public class DevelopmentController {

    
    @Autowired
    private ContactsService contactsService;    
    
    @GetMapping("/dev/deletecontact/{targetId}/{contactId}")
    public String deleteContact(@PathVariable Long targetId, @PathVariable Long contactId) {
        contactsService.removeContact(targetId, contactId);       
        return "redirect:/home";
    }  
    
    
    
}
