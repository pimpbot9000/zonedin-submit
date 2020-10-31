/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import projekti.models.IMyQuery;
import projekti.models.ProfileImage;
import projekti.models.UserAccount;

/**
 *
 * @author tvali
 */

public interface CustomQueryRepo extends JpaRepository <UserAccount, Long>{
    
    @Query(value = "SELECT ID AS id, USERNAME as username, CONTACTS_ID as contactsId "
            + "FROM USER_ACCOUNT LEFT OUTER JOIN (SELECT * FROM USER_ACCOUNT_CONTACTS WHERE CONTACTS_ID = ?1) AS FRIEND "
            + "ON USER_ACCOUNT.ID = FRIEND.USER_ACCOUNT_ID",  nativeQuery = true)
    List<IMyQuery> getWithFriends(Long id);   
     
}

/* SELECT INVITEE.ID, INVITEE.FIRSTNAME, INVITEE.PROFILE_IMAGE_ID FROM USER_ACCOUNT as U
    JOIN USER_ACCOUNT_SENT_INVITES as I
    ON 
    U.ID = I.USER_ACCOUNT_ID
    JOIN USER_ACCOUNT as INVITEE
    ON I.SENT_INVITES_ID = INVITEE.ID
    WHERE U.ID = 33

    */

/*@Query(
  value = "SELECT * FROM Users u WHERE u.status = ?1", 
  nativeQuery = true)
User findUserByStatusNative(Integer status);*/