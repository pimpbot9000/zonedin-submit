/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.models.ProfileImage;
import projekti.models.UserAccount;
import projekti.repositories.ProfileImageRepository;
import projekti.repositories.UserAccountRepository;
import projekti.services.UserAccountService;


/**
 *
 * @author tvali
 */
@Controller
public class ProfileImageController {

    //@Autowired
    //FileRepository fileRepository;
    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ProfileImageRepository profileImageRepo;
    
    /**
     * Any authenticated user has access to images, but that's ok.
     */
    @GetMapping(path = "/profileimages/{username}/content", produces = "image/png")
    @ResponseBody
    public byte[] getStoredImage(@PathVariable String username) {
        return userAccountService.getUserAccount(username).getProfileImage().getContent();
    }

    @GetMapping("/profileimages/{username}")
    public String getImage(@PathVariable String username) {

        UserAccount account = userAccountRepo.findByUsername(username);

        if (account.getProfileImage() == null) {
            return "redirect:/static/images/default-user-image.png";
        } else {
            return "redirect:/profileimages/" + username + "/content";
        }
    }

    @GetMapping(path = "/images/{profileImageId}", produces = "image/png")
    @ResponseBody
    public byte[] getImageById(@PathVariable Long profileImageId) {

        Optional<ProfileImage> image = profileImageRepo.findById(profileImageId);

        return image.isPresent() ? image.get().getContent() : null;
        
    }

    @PostMapping("/profileimages")
    public String save(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {

        UserAccount account = userAccountService.getUserAccount(authentication.getName());

        ProfileImage profileImage = account.getProfileImage();

        if (profileImage == null) {

            profileImage = new ProfileImage();

        }

        profileImage.setContent(file.getBytes());
        profileImageRepo.save(profileImage);

        account.setProfileImage(profileImage);
        userAccountRepo.save(account);

        return "redirect:/myprofile";
    }
}
