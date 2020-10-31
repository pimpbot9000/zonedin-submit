/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.models.UserAccount;
import projekti.services.ContactsService;
import projekti.services.UserAccountService;

/**
 *
 * @author tvali
 */
@Controller 
public class ProfileController {
    
    @Autowired
    private UserAccountService userAccountService;
    
    @Autowired
    private ContactsService contactsService;
    
    @GetMapping("/profiles/{idString}")
    public String getProfile(
            Authentication auth, 
            Model model, 
            @PathVariable String idString){
        
        // other user's account (the profile user is viewing)
        UserAccount otherAccount = userAccountService.getUserAccountByIdString(idString);
                
        //if profile is user's own profile
        if(otherAccount.getUsername().equals(auth.getName())){
            return "redirect:/home";
        }
        
        // current user
        UserAccount userAccount = userAccountService.getUserAccount(auth.getName());
        
        boolean isContact = contactsService.isUsersContact(userAccount, otherAccount);
        boolean isInvited = contactsService.hasUserInvited(userAccount, otherAccount);
        boolean hasSentInvite = contactsService.hasUserReceivedInvite(userAccount, otherAccount);
        List<UserAccount> usersContacts = contactsService.getContactsOfUser(userAccount);
        
        model.addAttribute("contacts", usersContacts);
        model.addAttribute("isContact", isContact);
        model.addAttribute("isInvited", isInvited);
        model.addAttribute("hasSentInvite", hasSentInvite);
        
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("account", otherAccount);
        return "profile";
    }
}
