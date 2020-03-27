package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.exeption.UsernameExistsException;
import by.itstep.onlineauctionsystem.model.UserDto;
import by.itstep.onlineauctionsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult) throws UsernameExistsException {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}
