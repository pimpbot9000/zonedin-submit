/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author tvali
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class UserAccount extends AbstractPersistable<Long>{
    
    @NotEmpty
    @Size(min = 2, max = 20)    
    private String firstname;
    
    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastname;
    
    @NotEmpty    
    private String password;
    
    @NotEmpty
    @Size(min = 2, max = 20)
    @Column(unique = true)
    private String idString;
    
    @NotEmpty
    @Size(min = 2, max = 20)
    @Column(unique = true)
    private String username;
    
    private String bio;
    
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> authorities;    
    
    @OneToOne(fetch = FetchType.LAZY)    
    private ProfileImage profileImage;
    
    @ManyToMany(fetch = FetchType.LAZY)    
    private List<UserAccount> contacts = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY) 
    private List<UserAccount> sentInvites = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserAccount> receivedInvites = new ArrayList<>();    
   
    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();
    
    @OneToMany(mappedBy = "author")
    private List<Reply> replies = new ArrayList<>();

    @Override
    public String toString() {
        return "UserAccount{" + "firstname=" + firstname + ", lastname=" + lastname + ", password=" + password + ", idString=" + idString + ", username=" + username + '}';
    }   
    
    
}
