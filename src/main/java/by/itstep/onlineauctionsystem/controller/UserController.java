package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.dto.UserDto;
import by.itstep.onlineauctionsystem.exeption.UserAlreadyExistException;
import by.itstep.onlineauctionsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String registerUserAccount(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("registration")
    public ModelAndView registerUserAccount(@ModelAttribute("userDto") @Valid UserDto userDto, Errors errors, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("registration", "user", userDto);
        }
        try {
            userService.saveNewUser(userDto);
        } catch (UserAlreadyExistException e) {
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            mav.addObject("message", "An account for that username/email already exists.");
            e.printStackTrace();
            return mav;
        }
        return new ModelAndView("registration", "user", userDto);
    }

    @GetMapping("login")
    public String getLoginPage(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping("account")
    public String showUserAccount(Principal principal) {
        return "account";
    }
}
