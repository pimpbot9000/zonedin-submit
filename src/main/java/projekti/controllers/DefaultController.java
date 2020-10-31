/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.UserAccount;
import projekti.repositories.UserAccountRepository;
import projekti.services.UserAccountService;

/**
 *
 * @author tvali
 */
@Controller
public class DefaultController {    
        
    @GetMapping("/")
    public String root(Model model, Authentication authentication, @RequestParam(required = false) Boolean signinsuccess){
        
        if(authentication != null && authentication.isAuthenticated()){
            return "redirect:/home";
        }      
        
        if(signinsuccess != null && signinsuccess == true){
            model.addAttribute("signin", true);
        }
        
        return "frontpage";
        
    }  
    
}
