/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.models.UserAccount;
import projekti.repositories.UserAccountRepository;

/**
 * Class violates DRY principle, but..
 *
 * @author tvali
 */
@Service
public class ContactsService {

    @Autowired
    private UserAccountRepository userAccountRepo;

    @Transactional
    public void createContact(Long id, Long otherId) {

        Optional<UserAccount> account = (Optional<UserAccount>) userAccountRepo.findById(id);

        Optional<UserAccount> otherAccount = (Optional<UserAccount>) userAccountRepo.findById(otherId);

        if (!account.isPresent() || !otherAccount.isPresent()) {
            return;
        }

        List<UserAccount> contacts = account.get().getContacts();

        List<UserAccount> otherContacts = otherAccount.get().getContacts();

        if (!contacts.contains(otherAccount.get())) {
            contacts.add(otherAccount.get());
        }

        if (!otherContacts.contains(account.get())) {
            otherContacts.add(account.get());
        }

    }

    @Transactional
    public void removeContact(Long userId, Long contactId) {

        Optional<UserAccount> account = userAccountRepo.findById(userId);
        Optional<UserAccount> contactAccount = userAccountRepo.findById(contactId);

        if (!account.isPresent() || !contactAccount.isPresent()) {
            return;
        }

        account.get().getContacts().remove(contactAccount.get());
        contactAccount.get().getContacts().remove(account.get());

    }
    
    public List<UserAccount> getContactsOfUser(UserAccount user) {
        // this should be done probably with DB query, but meh.
        return user.getContacts();
    }

    public List<UserAccount> getContactsOfUserWithFilter(UserAccount user, UserAccount filterUserOut) {
        // this should be done probably with DB query, but meh.
        return user.getContacts().stream().filter((UserAccount u) -> u.getId() != filterUserOut.getId()).collect(Collectors.toList());
    }

    /**
     * Remove a contact relation between user and contact (bidirectional).
     *
     * @param username
     * @param contactId
     */
    @Transactional
    public void removeContact(String username, Long contactId) {

        UserAccount account = userAccountRepo.findByUsername(username);
        Optional<UserAccount> contactAccount = userAccountRepo.findById(contactId);

        if (account == null || !contactAccount.isPresent()) {
            return;
        }

        account.getContacts().remove(contactAccount.get());
        contactAccount.get().getContacts().remove(account);

    }

    /**
     * Remove an invite the user has sent.
     *
     * @param username
     * @param contactId
     */
    @Transactional
    public void cancelPendingInvite(String username, Long contactId) {

        UserAccount account = userAccountRepo.findByUsername(username);
        Optional<UserAccount> contactAccount = userAccountRepo.findById(contactId);

        if (account == null || !contactAccount.isPresent()) {
            return;
        }

        account.getSentInvites().remove(contactAccount.get());
        contactAccount.get().getReceivedInvites().remove(account);

    }

    @Transactional
    public int acceptPendingApproval(String username, Long contactId) {

        UserAccount account = userAccountRepo.findByUsername(username);
        Optional<UserAccount> contactAccount = userAccountRepo.findById(contactId);

        if (account == null || !contactAccount.isPresent()) {
            return DOES_NOT_EXIST;
        }

        // if invite still exists        
        if (contactAccount.get().getSentInvites().contains(account)) {

            account.getReceivedInvites().remove(contactAccount.get());
            contactAccount.get().getSentInvites().remove(account);

            this.createContact(account.getId(), contactId);

            return SUCCESS;

        }

        return DOES_NOT_EXIST;

    }

    @Transactional
    public void rejectPendingApproval(String username, Long contactId) {

        UserAccount account = userAccountRepo.findByUsername(username);
        Optional<UserAccount> contactAccount = userAccountRepo.findById(contactId);

        if (account == null || !contactAccount.isPresent()) {
            return;
        }

        account.getReceivedInvites().remove(contactAccount.get());
        contactAccount.get().getSentInvites().remove(account);
    }

    @Transactional
    public int sendInvite(String inviterUsername, Long inviteeId) {

        UserAccount inviterAccount = userAccountRepo.findByUsername(inviterUsername);
        Optional<UserAccount> inviteeAccount = userAccountRepo.findById(inviteeId);

        if (inviterAccount == null || !inviteeAccount.isPresent()) {
            return DOES_NOT_EXIST;

        }

        // is invite already sent?
        if (inviterAccount.getSentInvites().contains(inviteeAccount.get())) {
            return CANNOT_DO_THAT_SORRY;
        }
        
        // has other user already invited this user
        if (inviterAccount.getReceivedInvites().contains(inviteeAccount.get())) {
            return CANNOT_DO_THAT_SORRY;
        }

        // are the users already "friends?"        
        if (areFriends(inviterAccount, inviteeAccount.get())) {
            return CANNOT_DO_THAT_SORRY;
        }

        inviterAccount.getSentInvites().add(inviteeAccount.get());
        inviteeAccount.get().getReceivedInvites().add(inviterAccount);
        return SUCCESS;

    }

    public boolean isUsersContact(UserAccount account, UserAccount otherAccount) {
        return account.getContacts().contains(otherAccount);
    }

    public boolean hasUserInvited(UserAccount account, UserAccount otherAccount) {
        return account.getSentInvites().contains(otherAccount);
    }

    public boolean hasUserReceivedInvite(UserAccount account, UserAccount otherAccount) {
        return account.getReceivedInvites().contains(otherAccount);
    }

    // helper functions
    private boolean areFriends(UserAccount user, UserAccount otherUser) {
        return user.getContacts().contains(otherUser);
    }

    public static int DOES_NOT_EXIST = -1;
    public static int SUCCESS = 1;
    public static int CANNOT_DO_THAT_SORRY = -2;

}
