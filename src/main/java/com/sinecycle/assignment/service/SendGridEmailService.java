package com.sinecycle.assignment.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Async
public class SendGridEmailService {

  @NonNull
  @Qualifier("sendGridApi")
  private final SendGrid sendGrid;

  public void sendHtml(String from, String to, String subject, String body) {
    Response response = sendEmail(from, to, subject, new Content("text/html", body));
    if (null != response) {
      System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
              + response.getHeaders());
    }
  }

  private Response sendEmail(String from, String to, String subject, Content content) {
    Mail mail = new Mail(new Email(from), subject, new Email(to), content);
    mail.setReplyTo(new Email("abc@gmail.com"));
    Request request = new Request();
    Response response = null;
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      response = this.sendGrid.api(request);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    return response;
  }
}
