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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.UserAccount;
import projekti.services.SearchService;
import projekti.services.UserAccountService;

/**
 *
 * @author tvali
 */
@Controller
public class SearchController {
    
    @Autowired
    private UserAccountService userAccountService;
    
    @Autowired
    private SearchService searchService;    
        
    @GetMapping("/search")
    public String search(@RequestParam String searchString, Model model, Authentication auth){
        
        UserAccount userAccount = userAccountService.getUserAccount(auth.getName());
        List<UserAccount> accounts = searchService.search(searchString);        
        model.addAttribute("accounts", accounts);
        model.addAttribute("userAccount", userAccount);
        return "search";
        
    }
    
}

