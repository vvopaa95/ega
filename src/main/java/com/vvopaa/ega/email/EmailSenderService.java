package com.vvopaa.ega.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EmailSenderService {
  public static final String TPL_MSG_FORMAT = "To confirm your account, please click here:\n http://localhost:8080/security/confirm-account?token=%s\n";
  private final JavaMailSender javaMailSender;

  public Mono<Void> sendEmail(SimpleMailMessage email) {
    Mono<Void> response = Mono.empty();
    try {
      javaMailSender.send(email);
    } catch (MailParseException | MailAuthenticationException | MailSendException ex) {
      log.warn(ex.getMessage());
      response = Mono.error(ex);
    }
    return response;
  }

  public Mono<Void> sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment, String filename) {
    Mono<Void> response = Mono.empty();
    MimeMessage message = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text);

      FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
      helper.addAttachment(filename, file);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.warn(e.getMessage());
      response = Mono.error(e);
    }
    return response;
  }
}
