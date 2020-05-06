package by.itstep.onlineauctionsystem.emailsending;

import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.repository.ItemRepository;
import by.itstep.onlineauctionsystem.websocket.BidRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final ItemRepository itemRepository;
    public final JavaMailSender emailSender;


    public EmailService(ItemRepository itemRepository, JavaMailSender emailSender) {
        this.itemRepository = itemRepository;
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Async
    public void sendBidNotification(Principal principal, BidRequest bidRequest) {
        String username = principal.getName();
        sendEmail(bidRequest, username);
    }

    @Async
    public void sendAutoBidNotification(BidRequest bidRequest, String username) {
        sendEmail(bidRequest, username);
    }

    public CompletableFuture<SimpleMailMessage> sendEmail(BidRequest bidRequest, String username) {
        Optional<Item> itemOpt = itemRepository.findById(Long.valueOf(bidRequest.getId()));
        Item item = itemOpt.get();
        String previousBuyer = item.getBuyerEmail();

        if (previousBuyer == null) {
            item.setBuyerEmail(username);
            itemRepository.save(item);
        } else if (!previousBuyer.equals(username)) {
            item.setBuyerEmail(username);
            itemRepository.save(item);
            SimpleMailMessage message = new SimpleMailMessage();
            String subject = "Your bid was outbid";
            String text = "Unfortunately your bid was outbid.";
            message.setTo(previousBuyer);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            LOGGER.info("Outbid message was send to " + previousBuyer);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Bid successfully placed";
        String text = "Congratulations! Your bid BYR " + bidRequest.getBid() + " currently wins!";
        message.setTo(username);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        LOGGER.info("Bid message was send to " + username);
        return CompletableFuture.completedFuture(message);
    }
}