/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.models.Post;
import projekti.models.Reply;
import projekti.models.UserAccount;
import projekti.repositories.PostRepository;
import projekti.repositories.ReplyRepository;

/**
 *
 * @author tvali
 */

@Service
public class FeedService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ReplyRepository replyRepository;
    
    public List<Post> getPosts(UserAccount account){                
        return postRepository.findAll();
    }
    
    public Page<Post> getLatestPosts(int pageSize){
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("dateTime").descending());
        return postRepository.findAll(pageable);        
    }
    
    public void addPost(UserAccount account, String content){        
        LocalDateTime date = LocalDateTime.now(ZoneId.of("Europe/Helsinki"));
        Post newPost = new Post();
        newPost.setAuthor(account);
        newPost.setContent(content);
        newPost.setDateTime(date);
        postRepository.save(newPost);       
        
    }
    
    @Transactional
    public int addReply(UserAccount account, String content, Long postId){
        
        Optional<Post> postOptional = postRepository.findById(postId);
        
        if(!postOptional.isPresent()){
            return ERROR;
        }
        
        Post post = postOptional.get();
        
        LocalDateTime date = LocalDateTime.now(ZoneId.of("Europe/Helsinki"));
        Reply newReply = new Reply();
        newReply.setContent(content);
        newReply.setDateTime(date);
        newReply.setAuthor(account);
        newReply.setPost(post);
        
        replyRepository.save(newReply);
        
        return SUCCESS;
    }
    
    private static int SUCCESS = 1;
    private static int ERROR = -1;    
}
