package com.register.controller;

import com.register.entity.User;
import com.register.service.CheckSession;
import com.register.service.EmailChecking;
import com.register.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    private final EmailChecking emailChecking;


    private final CheckSession checkSession;


    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String register(User user) {
        Optional<User> user1 = emailChecking.checkEmail(user);
        if (user1.isPresent()) {
            return "redirect:register?eyni";
        }

        userService.save(user);
        return "redirect:register?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("login")
    public String loginToWebSite(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest httpServletRequest) {
        Optional<User> user = userService.login(email, password);
        if (!user.isPresent()) {
            model.addAttribute("error", "Bele istifadeci movcud deyil");
            return "login";
        }
        httpServletRequest.getSession().setAttribute("istifadeci", user.get());
        return "welcome";
    }


    @GetMapping("/welcome")
    public String welcome(HttpServletRequest httpServletRequest) {
        User user = checkSession.sessionCheck(httpServletRequest);
        if (user == null) {
            return "login";
        }
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, Model model) {
        httpServletRequest.getSession().invalidate();
        model.addAttribute("logout", "siz ugurla sistemden cixis elediniz");
        return "login";
    }


}
