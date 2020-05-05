package by.itstep.onlineauctionsystem.emailsending;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    private final EmailService emailService;
    private final JavaMailSender emailSender;

    public EmailController(EmailService emailService, JavaMailSender emailSender) {
        this.emailService = emailService;
        this.emailSender = emailSender;
    }

    @PostMapping("send-email-to/{id}")
    public String sendEmail(SimpleMailMessage message, @PathVariable int id) {
        emailSender.send(message);
        return "redirect:/item/" + id;
    }
}
