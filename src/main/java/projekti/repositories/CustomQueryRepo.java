/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projekti.models.IMyQuery;
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
