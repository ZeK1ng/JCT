package com.example.Market.Controllers.Services;
import com.example.Market.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//#javaMailTest
//stapilo2020
@Service
public class MailService {

    @Autowired
    private ClientRepository repository;
    public static void sendMail(String targetMail,String redirectLink,int userId) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth",true);
        prop.put("mail.smtp.starttls.enable",true);
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");
        String senderAcc = "dgogi17@freeuni.edu.ge";
        String sendPass = "charxali2019";
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
//                return super.getPasswordAuthentication();
                return new PasswordAuthentication(senderAcc,sendPass);
            }
        });
        Message message = constructMessage(session,senderAcc,targetMail,redirectLink,userId);
        System.out.println("-----SENDING MAIL-------");
        try {
            Transport.send(message);
        }catch (MessagingException ex){
            System.out.println("----MESSAGING EXCEPTION"+ex);
        }
        System.out.println("MAIL-------SENT");
    }


    private static Message constructMessage(Session session, String senderAcc,String targetMail,String redirectLink,int userId) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderAcc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(targetMail));
            message.setSubject("Please verify your registration");
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><Ua href=\"[[RL]]\">VERIFY</a></h3>"
                    + "Thank you";
            redirectLink = redirectLink+"?userid="+userId;
            content = content.replace("[[URL]]", redirectLink);
            message.setText(content);
            return message;
        } catch (Exception ex) {
            System.out.println("-----Error Sending Mail-----");
        }
        return null;
    }


}
