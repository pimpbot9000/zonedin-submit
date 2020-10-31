/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
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
public class Post extends AbstractPersistable<Long> {

    private LocalDateTime dateTime;
    
    private String content;

    @ManyToOne
    private UserAccount author;
    
    @OneToMany(mappedBy="post")
    private List<Reply> replies = new ArrayList<>();
    
    public List<Reply> getLatestTenReplies(){       
        
        
        return replies.stream().sorted( new Comparator<Reply>(){
            @Override
            public int compare(Reply r0, Reply r1) {
                return r1.getDateTime().compareTo(r0.getDateTime());
            }
            
        }).limit(10).collect(Collectors.toList());
    }
    
    public String getFormattedDateTime(){
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    
    public boolean noReplies(){
        return replies.size() == 0;
    }
    
}
