package in.sparklogic.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	final long MAIL_RETRY_WAIT_TIME = 60 * 1000L;

	@Autowired
	private JavaMailSender sender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${sender.username}")
	private String fromUserName;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Async
	public void sendEmail(String fromUserNameParam, InternetAddress[] to, InternetAddress[] cc, String subject,
			String template) throws UnsupportedEncodingException, MessagingException {
		boolean mailSent = false;
		int i = 1;
		for (; i <= 10 && mailSent == false; i++) {
			try {
				fromUserNameParam = fromUserNameParam == null ? fromUserName : fromUserNameParam;
				InternetAddress from = new InternetAddress(fromEmail, fromUserNameParam);
				MimeMessage message = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom(from);
				helper.setTo(to);
				helper.setCc(cc);
				helper.setText(template, true); // set to html
				helper.setSubject(subject);
				List<String> toAddresses = new ArrayList<>();
				for (InternetAddress t : to) {
					toAddresses.add(t.getAddress());
				}
				logger.info(
						"SendEmailLog: Trial:" + i + " Subject: " + subject + " To: " + String.join(",", toAddresses));
				sender.send(message);
				mailSent = true;
				logger.info("Mail Sent successfully: " + message.getSubject());
			} catch (MailSendException mse) {
				logger.error("MailSendException caught: ", mse);
				sleepForAWhile(MAIL_RETRY_WAIT_TIME);
			}
		}
		if (mailSent == false) {
			logger.error("Cannot send email after " + i + " retries." + " Subject: " + subject);
		}
	}

	private void sleepForAWhile(long miliSeconds) {
		try {
			logger.info("Sleeping for " + miliSeconds + " ms");
			Thread.sleep(miliSeconds);
			logger.info("Woke up :)");
		} catch (Exception e) {
			logger.error("Error sleeping thread: ", e);
		}
	}

	// -- Code to attach image to email --
	// String path = ".\\" + String.join("\\", "src", "main",
	// "resources", "templates", "logo.png");
	// FileSystemResource res = new FileSystemResource(new File(path));
	// helper.addInline("logo", res);
}
