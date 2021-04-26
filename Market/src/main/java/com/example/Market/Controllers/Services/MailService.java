package com.example.Market.Controllers.Services;
import com.example.Market.Constants.StringConstants;
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

    public void sendMail(String targetMail,String redirectLink,int userId,int flag) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth",true);
        prop.put("mail.smtp.starttls.enable",true);
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");
        String senderAcc = StringConstants.MAIL;
        String sendPass = StringConstants.MAILPWD;
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
//                return super.getPasswordAuthentication();
                return new PasswordAuthentication(senderAcc,sendPass);
            }
        });
        Message message = constructMessage(session,senderAcc,targetMail,redirectLink,userId,flag);
        System.out.println("-----SENDING MAIL-------");
        try {
            Transport.send(message);
        }catch (MessagingException ex){
            System.out.println("----MESSAGING EXCEPTION"+ex);
        }
        System.out.println("MAIL-------SENT");
    }


    private Message constructMessage(Session session, String senderAcc,String targetMail,String redirectLink,int userId,int flag) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderAcc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(targetMail));
            String content ="";
            if(flag == 1){
                message.setSubject("Please verify your registration");
                content= "Dear [[name]],<br>"
                        + "Please click the link below to verify your registration:<br>"
                        + "<h3><a href=\"[[URL]]\">[[URL]]</a></h3>"
                        + "Thank you";
            }else{
                message.setSubject("Reset you password");
                content= "Dear [[name]],<br>"
                        + "Please click the link below to reset your password:<br>"
                        + "<h3><a href=\"[[URL]]\">[[URL]]</a></h3>"
                        + "Thank you";
            }

            redirectLink = redirectLink+"?userid="+userId;
            content = content.replace("[[URL]]", redirectLink);
            message.setContent(content,"text/html");
//            message.setText(content);
            return message;
        } catch (Exception ex) {
            System.out.println("-----Error Sending Mail-----");
        }
        return null;
    }


}
