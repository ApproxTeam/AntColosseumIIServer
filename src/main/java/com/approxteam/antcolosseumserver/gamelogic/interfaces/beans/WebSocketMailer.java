/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces.beans;

import com.approxteam.antcolosseumserver.configuration.PropertiesBuilder;
import com.approxteam.antcolosseumserver.configuration.PropertyComment;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.Mailer;
import com.approxteam.antcolosseumserver.mailer.MailWrapper;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author adamr
 */
@PropertyComment(desc = "Mailer settings")
@Stateless
public class WebSocketMailer implements Mailer{
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(WebSocketMailer.class);

    private final Properties properties = PropertiesBuilder.getProperties(WebSocketMailer.class);
    
    
    
    private Session buildSession() {
        return Session.getInstance(properties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("username"), properties.getProperty("password"));
                }    
            });
    }
    
    @Override
    public boolean send(MailWrapper wrapper) {
        try {
            Message message = new MimeMessage(buildSession());
            InternetAddress from = new InternetAddress(wrapper.getFrom());
            message.setFrom(from);
            InternetAddress to = new InternetAddress(wrapper.getTo());
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject(wrapper.getTitle());
            message.setText(wrapper.getContent());
            
            Transport.send(message);
        
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
}
