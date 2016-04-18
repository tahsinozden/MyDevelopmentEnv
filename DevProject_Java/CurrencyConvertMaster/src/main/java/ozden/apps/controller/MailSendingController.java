package ozden.apps.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ozden.apps.tools.SmtpMailSender;

@RestController
public class MailSendingController {
	@Autowired
	private SmtpMailSender stmpMailSender;
	
	@RequestMapping("/send-email")
	public String mailSender() throws MessagingException{
		String msg = "Hello to sender!";
		this.stmpMailSender.send("tahsinozden@gmail.com", "Test Email", msg);
		return msg;
	}
}
