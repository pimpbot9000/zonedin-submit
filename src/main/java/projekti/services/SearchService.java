/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.models.UserAccount;
import projekti.repositories.UserAccountRepository;

/**
 *
 * @author tvali
 */
@Service
public class SearchService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * Simple but at least somewhat sensibly working search
     */
    public List<UserAccount> search(String searchString) {
        String[] strArr = searchString.split(" ");

        if (strArr.length >= 2) {
            
            return userAccountRepository
                    .findBySearchStrings(
                            "%" + strArr[0].toLowerCase() + "%",
                            "%" + strArr[1].toLowerCase() + "%");
            
        } else if( strArr.length == 1){
            
            return userAccountRepository.findBySearchString("%" + strArr[0].toLowerCase() + "%");
            
        }
        
        return null;
        
        
    }

}
