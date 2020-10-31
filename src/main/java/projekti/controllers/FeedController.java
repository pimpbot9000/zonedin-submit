/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.models.Post;
import projekti.models.UserAccount;
import projekti.services.FeedService;
import projekti.services.UserAccountService;

/**
 *
 * @author tvali
 */
@Controller
public class FeedController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private FeedService feedService;

    @GetMapping("/feed")    
    public String getFeed(Authentication auth, Model model, @RequestParam(required = false) String error) {

        UserAccount account = userAccountService.getUserAccount(auth.getName());
        
        Page<Post> posts = feedService.getLatestPosts(25);
        
        model.addAttribute("posts", posts);
        model.addAttribute("userAccount", account);
        
        if(error != null && error.equals("empty")){
            model.addAttribute("error", "empty");
        }
        return "feed";
    }
    
    @PostMapping("/feed")
    public String postMessage(Authentication auth, Model model, @RequestParam String content){
        
        if(content.isEmpty()){
            return "redirect:/feed?error=empty";
        }
        UserAccount account = userAccountService.getUserAccount(auth.getName());
        feedService.addPost(account, content);
        
        return "redirect:/feed";
    }
    
    @PostMapping("/feed/reply/{postId}")
    public String postReply(Authentication auth, Model model, @PathVariable Long postId, @RequestParam String content){        
        feedService.addReply(userAccountService.getUserAccount(auth.getName()), content, postId);
        return "redirect:/feed";
    }

}
