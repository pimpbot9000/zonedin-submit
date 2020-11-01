/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Post;
import projekti.models.UserAccount;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author tvali
 */
public interface PostRepository extends JpaRepository <Post, Long>{
    @EntityGraph(attributePaths = {"author", "replies"})
    List<Post> findAll();
    
    @EntityGraph(attributePaths = {"author", "replies"})
    Page<Post> findByAuthorIn(Collection<UserAccount> myContactsAndMe, Pageable pageable);
}


