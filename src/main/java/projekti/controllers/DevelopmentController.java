/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.models.IMyQuery;
import projekti.models.UserAccount;
import projekti.repositories.CustomQueryRepo;
import projekti.repositories.UserAccountRepository;
import projekti.services.ContactsService;

/**
 *
 * @author tvali
 */
@Controller
@Profile({"production", "dev"})
public class DevelopmentController {

    @Autowired
    private UserAccountRepository userAccountRepo;
    
    @Autowired
    private ContactsService contactsService;
    
    @Autowired
    private CustomQueryRepo custom;

    /*@GetMapping("/dev/createcontact/{targetId}/{contactId}")
    public String createContact(@PathVariable Long targetId, @PathVariable Long contactId) {
        contactsService.createContact(targetId, contactId);        
        return "redirect:/home";
    }*/
    
    @GetMapping("/dev/deletecontact/{targetId}/{contactId}")
    public String deleteContact(@PathVariable Long targetId, @PathVariable Long contactId) {
        contactsService.removeContact(targetId, contactId); 
        //contactsService.removeContact(contactId, targetId);
        return "redirect:/home";
    }
    
    @GetMapping("/dev/test/{id}")
    @ResponseBody
    public String test(@PathVariable Long id){
        List<IMyQuery> res = custom.getWithFriends(id);
        
        for( IMyQuery item : res){
            System.out.println("" + item.getUsername() + " " + item.getId() + " " + item.getContactsId());
        }
        return "redirect:/";
    }   
    
    
    
}
