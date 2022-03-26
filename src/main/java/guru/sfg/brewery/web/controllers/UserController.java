package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/register2fa")
    public String register2Fa(Model model) {
        model.addAttribute("googleurl", "todo");
        return "user/register2fa";
    }

    @PostMapping("/register2fa")
    public String confirm2Fa(@RequestParam Integer verifyCode) {
        // TODO -IMPL
        return "index";
    }
}
