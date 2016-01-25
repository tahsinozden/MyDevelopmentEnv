/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import sun.nio.cs.HistoricallyNamedCharset;

/**
 *
 * @author tahsin
 */
public class MailSender {

    private String sender;
    private String receiver;
    private String mailTitle;
    private String mailBody;
    private String senderAcctPswd;
    
    public MailSender() {
    }

    public MailSender(String from, String to, String body, String title, String pswd) {
        sender = from;
        receiver = to;
        mailBody = body;
        mailTitle = title;
        senderAcctPswd = pswd;
    }

    public void sendMail() {

        // email SMTP server configurations
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(getSender(), getSenderAcctPswd());
                    }
                });

        try {
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver));
            message.setSubject(this.getMailTitle());
            message.setText(this.getMailBody());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the mailTitle
     */
    public String getMailTitle() {
        return mailTitle;
    }

    /**
     * @param mailTitle the mailTitle to set
     */
    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    /**
     * @return the mailBody
     */
    public String getMailBody() {
        return mailBody;
    }

    /**
     * @param mailBody the mailBody to set
     */
    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }


    /**
     * @return the senderAcctPswd
     */
    public String getSenderAcctPswd() {
        return senderAcctPswd;
    }

    /**
     * @param senderAcctPswd the senderAcctPswd to set
     */
    public void setSenderAcctPswd(String senderAcctPswd) {
        this.senderAcctPswd = senderAcctPswd;
    }
}
