package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.UserAccount;
import projekti.services.UserAccountService;

/**
 *
 * @author tvali
 */
@Controller
public class SignInController {

    @Autowired
    UserAccountService userAccountService;

    @GetMapping("/signin")
    public String signIn(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String username,
            @ModelAttribute UserAccount userAccount,
            Model model) {

        return "signin";
    }

    @PostMapping("/signin")
    public String addAccount(
            @Valid @ModelAttribute UserAccount account, BindingResult bindingResult, Model model,
            @RequestParam String password2
    ) {

        boolean error = false;

        if (bindingResult.hasErrors()) {
            error = true;
        }

        if (userAccountService.userExists(account.getUsername())) {
            model.addAttribute("userExists", "true");
            error = true;
        }

        if (userAccountService.idStringExists(account.getIdString())) {
            model.addAttribute("idStringExists", "true");
            error = true;
        }

        if (!account.getPassword().equals(password2)) {
            model.addAttribute("passwordMismatch", "true");
            error = true;
        }

        if (account.getPassword().length() < 5 && account.getPassword().length() != 0 ) { // note != 0 because this error case in handled by ModelAttribute validator
            model.addAttribute("passwordTooShort", "true");
            error = true;
        }

        if (error) {
            return "signin";
        }

        userAccountService.createUser(
                account.getUsername(),
                account.getPassword(),
                account.getFirstname(),
                account.getLastname(),
                account.getIdString());

        return "redirect:/?signinsuccess=true";
    }

}
