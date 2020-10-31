/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.models.UserAccount;
import projekti.repositories.UserAccountRepository;

/**
 *
 * @author tvali
 */

@Service
public class UserAccountService {

    
    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private PasswordEncoder encoder;

    public UserAccount getUserAccount(String username) {
        return userAccountRepo.findByUsername(username);
    }
    
    public boolean idStringExists(String idString){
        return userAccountRepo.findByIdString(idString) != null;
    }
    
    public UserAccount getUserAccountByIdString(String idString) {
        return userAccountRepo.findByIdString(idString);
    }   

    public boolean userExists(String username) {

        return userAccountRepo.findByUsername(username) != null;

    }
    
    /**
     * Create a regular user with granted authority USER 
     */
    public UserAccount createUser(
            String username, 
            String password, 
            String firstname, 
            String lastname,
            String idString) {

        UserAccount u = new UserAccount();
        u.setPassword(encoder.encode(password));
        u.setUsername(username);
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setIdString(idString);
        u.setAuthorities(Arrays.asList("USER"));       
        
        userAccountRepo.save(u);
        return getUserAccount(username);

    }
    
    public void updateBio(String username, String bio){
        UserAccount u = getUserAccount(username);
        u.setBio(bio);
        userAccountRepo.save(u);        
    }   
    
    
}
