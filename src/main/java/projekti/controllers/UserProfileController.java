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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.UserAccount;
import projekti.services.UserAccountService;

/**
 *
 * @author tvali
 */
@Controller
public class UserProfileController {
    
    @Autowired
    private UserAccountService userAccountService;
    
    @GetMapping("/home")
    public String home(Authentication auth, Model model){
        
        UserAccount account = userAccountService.getUserAccount(auth.getName());
        
        model.addAttribute("userAccount", account);
        return "home";        
    }
    
    @GetMapping("/myprofile")
    public String myProfile(Authentication auth, Model model){      
        
        
        UserAccount account = userAccountService.getUserAccount(auth.getName());        
        model.addAttribute("userAccount", account);
        return "myprofile";
    }
    
    @PostMapping("/myprofile/bio")
    public String updateBio(Authentication auth, @RequestParam String bio){
        
        userAccountService.updateBio(auth.getName(), bio);
        return "redirect:/myprofile";
    }
}
