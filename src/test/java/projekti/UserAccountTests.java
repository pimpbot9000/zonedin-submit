/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

/**
 *
 * @author tvali
 */

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import projekti.models.UserAccount;
import projekti.repositories.UserAccountRepository;
import projekti.services.UserAccountService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest

public class UserAccountTests {
    
    @Autowired
    private UserAccountRepository userAccountRepository;
    
    @Autowired
    private UserAccountService userAccountService;
    
    @Before
    public void setUp() {
        userAccountRepository.deleteAll();
    }

    @After
    public void tearDown() {
        userAccountRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void createUserAccountCorrectly(){
        
        String username = "username10";
        String password = "swordfish";
        String firstname = "alice";
        String lastname = "smith";
        String idString = "username1";
                
        
        UserAccount account = userAccountService.createUser(username, password, firstname, lastname, idString);
        
        // test the useraccount returned by service
        assertEquals(username, account.getUsername());
        
        // user should have only one authority, USER
        assertEquals(1, account.getAuthorities().size());
        assertEquals("USER", account.getAuthorities().get(0));
        
        // service should return user already exists
        assertEquals(true, userAccountService.userExists(username));
        
        // test the repository directly        
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        
        // there should be onlu 1 user in repository
        assertEquals(1, userAccounts.size());
        assertEquals(username, userAccounts.get(0).getUsername());        
        
    }
    
}
