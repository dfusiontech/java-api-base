package com.dfusiontech.server.service;

import com.dfusiontech.server.model.jpa.entity.UserPasswordResetLinks;
import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.rest.exception.ApplicationExceptionCodes;
import com.dfusiontech.server.rest.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * Email send Service
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-25
 */
@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private UserPasswordResetLinksService userPasswordResetLinksService;

	/**
	 * Get Answer Weights List
	 *
	 * @return Answer Weights List
	 */
	public void sendResetPasswordEmail(Users user) {

		UserPasswordResetLinks linkDetails = userPasswordResetLinksService.create(user);

		final Context ctx = new Context(Locale.ENGLISH);
		ctx.setVariable("name", user.getFullName());
		ctx.setVariable("resetPasswordLink", userPasswordResetLinksService.getLinkUrl(linkDetails));
		final String htmlContent = this.templateEngine.process("forgot-password-email.html", ctx);

		try {
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = emailSender.createMimeMessage();

			InternetAddress fromAddress = new InternetAddress("noreply@vrisk.com", "Do Not Reply");
			InternetAddress toAddress = new InternetAddress(user.getEmail(), user.getFullName());

			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			message.setSubject("Password Restore on vRisk");
			message.setFrom(fromAddress);
			message.setReplyTo(fromAddress);
			message.setTo(toAddress);
			message.setText(htmlContent, true);

			emailSender.send(mimeMessage);

		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new InternalServerErrorException(MessageFormat.format("Failed to send reset password email to [{0}]", user.getEmail()), ApplicationExceptionCodes.RESET_PASSWORD_LINK_EMAIL_FAILED);
		}

	}

}
