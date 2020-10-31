/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.models.UserAccount;

/**
 *
 * @author tvali
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    //@EntityGraph(attributePaths = {"profileImage"})
    UserAccount findByUsername(String username);
    
    UserAccount findByIdString(String idString);

    List<UserAccount> findByFirstnameContainingOrLastnameContaining(String str1, String str2);

    @Query(value = "SELECT * FROM USER_ACCOUNT WHERE LOWER(firstname) LIKE :str1 "
            + "UNION "
            + "SELECT * FROM USER_ACCOUNT WHERE LOWER(lastname) LIKE :str2 "
            + "UNION " 
            + "SELECT * FROM USER_ACCOUNT WHERE LOWER(firstname) LIKE :str2 "
            + "UNION "
            + "SELECT * FROM USER_ACCOUNT WHERE LOWER(firstname) LIKE :str1", nativeQuery = true)
    List<UserAccount> findBySearchStrings(@Param("str1") String str1, @Param("str2") String str2);
    
    @Query(value = "SELECT * FROM USER_ACCOUNT WHERE LOWER(firstname) LIKE :str "
            + "UNION "
            + "SELECT * FROM USER_ACCOUNT WHERE LOWER(lastname) LIKE :str "
            , nativeQuery = true)
    List<UserAccount> findBySearchString(@Param("str") String str);
    
    
}

/*
SELECT * FROM USER_ACCOUNT WHERE firstname LIKE '%?1%'
UNION
SELECT * FROM USER_ACCOUNT WHERE lastname LIKE '%?2%'
 */
