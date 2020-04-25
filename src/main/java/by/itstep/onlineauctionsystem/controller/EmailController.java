package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    final EmailService emailService;
    final JavaMailSender emailSender;

    public EmailController(EmailService emailService, JavaMailSender emailSender) {
        this.emailService = emailService;
        this.emailSender = emailSender;
    }

    @PostMapping("/sendEmail/{id}")
    public String sendEmail(SimpleMailMessage message, @PathVariable int id) {
        emailSender.send(message);
        return "redirect:/item/" + id;
    }
}
