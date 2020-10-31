/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.hibernate.Hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.repositories.UserAccountRepository;
import projekti.services.ContactsService;
import projekti.services.UserAccountService;
import static org.junit.Assert.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;
import projekti.models.UserAccount;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactsServiceTests {

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private UserAccountRepository accountRepo;

    @Autowired
    private ContactsService contactsService;

    private static final String username1 = "username1";
    private static final String password1 = "swordfish";
    private static final String firstname1 = "Alice";
    private static final String lastname1 = "Smith";
    private static final String idString1 = "username1";
    
    private static final String username2 = "username2";
    private static final String password2 = "swordfish";
    private static final String firstname2 = "Bob";
    private static final String lastname2 = "Smith";
    private static final String idString2 = "username2";
    
    private static final String username3 = "username3";
    private static final String password3 = "swordfish3";
    private static final String firstname3 = "Bob3";
    private static final String lastname3= "Smith3";
    private static final String idString3 = "username3";
    
    private UserAccount account;
    private UserAccount otherAccount;
    private UserAccount thirdAccount;
   
    @Before
    public void before() {
        accountService.createUser(username1, password1, firstname1, lastname1, idString1);
        accountService.createUser(username2, password2, firstname2, lastname2, idString2);
        accountService.createUser(username3, password3, firstname3, lastname3, idString3);
        
        account = accountService.getUserAccount(username1);
        otherAccount = accountService.getUserAccount(username2);
        thirdAccount = accountService.getUserAccount(username3);
        
        contactsService.sendInvite(account.getUsername(), otherAccount.getId());  // 1->2
        contactsService.createContact(thirdAccount.getId(), otherAccount.getId()); // 3<->2
        
        accountRepo.flush();
        
    }

    @After
    public void after() {
        accountRepo.deleteAll();
    }

    @Test
    @Transactional
    public void contactExists() {

        // initial test that test database holds user
        //UserAccount 
        assertEquals(username1, account.getUsername());
        // initial test that test database holds other user        
        assertEquals(username2, otherAccount.getUsername());
        
        
        
        // test contacts
        assertEquals(1, accountService.getUserAccount(username3).getContacts().size());
        assertEquals(1, accountService.getUserAccount(username2).getContacts().size());
    }
    
    @Test
    @Transactional
    public void inviteExist(){
        
        UserAccount inviterAccount = accountService.getUserAccount(username1);
        UserAccount inviteeAccount = accountService.getUserAccount(username2);       
        
        // initial test that test database holds user
        //UserAccount 
        assertEquals(username1, account.getUsername());
        // initial test that test database holds other user        
        assertEquals(username2, otherAccount.getUsername());
        
        
        
        List<UserAccount> sentInvites = inviterAccount.getSentInvites();
        List<UserAccount> receivedInvites = inviteeAccount.getReceivedInvites();
        assertEquals(1, sentInvites.size());
        assertEquals(1, receivedInvites.size());
        assertTrue(sentInvites.contains(inviteeAccount));
        assertTrue(receivedInvites.contains(inviterAccount));
        
    }
}
