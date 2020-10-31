/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author tvali
 */
@Entity
@Getter
@Setter
public class Reply extends AbstractPersistable<Long>{
    private LocalDateTime dateTime;    
    private String content;
    
    @ManyToOne
    private UserAccount author;
    
    @ManyToOne
    private Post post;
    
    public String getFormattedDateTime(){
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
