/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Post;

/**
 *
 * @author tvali
 */
public interface PostRepository extends JpaRepository <Post, Long>{
    @EntityGraph(attributePaths = {"author", "replies"})
    List<Post> findAll();
}


