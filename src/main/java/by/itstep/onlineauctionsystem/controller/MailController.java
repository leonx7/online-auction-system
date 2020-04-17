package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MailController {

    @Autowired
    EmailService emailService;
    @Autowired
    public JavaMailSender emailSender;

    @PostMapping("/sendEmail/{id}")
    public String sendEmail(SimpleMailMessage message, @PathVariable int id) {
        emailSender.send(message);
        return "redirect:/item/" + id;
    }
}
