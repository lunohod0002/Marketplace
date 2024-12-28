
package com.example.demo.controllers.Impl;

import com.example.demo.services.Impl.AuthService;
import jakarta.validation.Valid;
import org.example.input.UserRegisterInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AuthController {

    private AuthService authService;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDto")
    public UserRegisterInputModel initForm() {
        return new UserRegisterInputModel();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterInputModel userRegistrationDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            return "redirect:/";
        }

        this.authService.register(userRegistrationDto);

        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }
}

/*    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        User user = authService.getUser(username);

        UserProfileView userProfileView = new UserProfileView(
                username,
                user.getEmail(),
                user.getFullName(),
                user.getAge()
        );

        model.addAttribute("user", userProfileView);

        return "profile";
        }
    }


*/
