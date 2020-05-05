package by.itstep.onlineauctionsystem.emailsending;

import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.repository.ItemRepository;
import by.itstep.onlineauctionsystem.websocket.BidRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class EmailService {

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

    public void sendBidNotification(Principal principal, BidRequest bidRequest){
        Optional<Item> itemOpt = itemRepository.findById(Long.valueOf(bidRequest.getId()));
        Item item = itemOpt.get();
        String previousBuyer = item.getBuyerEmail();
        if (previousBuyer == null) {
            item.setBuyerEmail(principal.getName());
            itemRepository.save(item);
        }
        else if(!previousBuyer.equals(principal.getName())){
            item.setBuyerEmail(principal.getName());
            itemRepository.save(item);
            SimpleMailMessage message = new SimpleMailMessage();
            String subject = "Your bid was outbid";
            String text = "Unfortunately your bid was outbid.";
            message.setTo(previousBuyer);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            System.out.println("********************************************************** Outbid message was send!");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Bid successfully placed";
        String text = "Congratulations! Your bid BYR " + bidRequest.getBid() + " currently wins!";
        message.setTo(principal.getName());
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        System.out.println("********************************************************** Bid message was send!");
    }

    public void sendAutoBidNotification(BidRequest bidRequest, String username){
        Optional<Item> itemOpt = itemRepository.findById(Long.valueOf(bidRequest.getId()));
        Item item = itemOpt.get();
        String previousBuyer = item.getBuyerEmail();
        if(!previousBuyer.equals(username)){
            item.setBuyerEmail(username);
            itemRepository.save(item);
            SimpleMailMessage message = new SimpleMailMessage();
            String subject = "Your bid was outbid";
            String text = "Unfortunately your bid was outbid.";
            message.setTo(previousBuyer);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            System.out.println("********************************************************** Auto Outbid message was send!");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Bid successfully placed";
        String text = "Congratulations! Your bid BYR " + bidRequest.getBid() + " currently wins!";
        message.setTo(username);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        System.out.println("**************************************************************** Auto Bid message was send!");
    }
}