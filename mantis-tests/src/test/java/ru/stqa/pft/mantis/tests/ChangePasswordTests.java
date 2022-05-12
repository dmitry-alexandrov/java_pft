package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HelperBase;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {

  @Test
  public void testChangePassword() throws IOException, InterruptedException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now); // "mantis";
    String password = "password";
    String new_password = "new_password";
    String email = String.format("user%s@localhost", now);
    app.james().createUser(user, password);
    app.changePassword().start(user, email);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
    app.james().drainEmail(user, password);
    app.james().deleteUser(user);
    app.james().createUser(user, password);
    app.changePassword().login("administrator", "root");
    Thread.sleep(1000);
    app.changePassword().moveToManageUsers(user);
    Thread.sleep(1000);
    List<MailMessage> mailMessages2 = app.james().waitForMail(user, password, 60000);
    String confirmationLink2 = findConfirmationLink(mailMessages2, email);
    Thread.sleep(1000);
    app.changePassword().finishConfirmation(confirmationLink2, user, new_password);
  //  Thread.sleep(2000);
    assertTrue(app.newSession().login(user, new_password));

}

  private  String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }


}