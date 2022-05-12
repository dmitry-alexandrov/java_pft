package ru.stqa.pft.addressbook.tests.tests;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.tests.model.ContactData;
import ru.stqa.pft.addressbook.tests.model.Contacts;
import ru.stqa.pft.addressbook.tests.model.GroupData;
import ru.stqa.pft.addressbook.tests.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupsTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    File photo = new File("src/test/resources/stru.png");
    if (app.db().contacts().size() == 0) {
      app.goTo().contactPage();
      app.contact().create(new ContactData().withFirstName("Dmitry").withLastName("Aleksandrov").withAddress("Moscow street 1").withHomePhone("11111111").withHomePhone2("22222222").withMobilePhone("33333333").withWorkPhone("44444444").withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withPhoto(photo));

      if (app.db().groups().size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1").withHeader("test1").withFooter("test1"));
      }
    }
  }

  @Test
  public void testContactAddToGroups() throws InterruptedException {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    int groupsSize = groups.size();
    ContactData contact = app.contact().findContactThatCanBeAddedToSomeGroup(contacts, groupsSize);
    if (contact == null) {
      app.contact().create(new ContactData()
              .withFirstName("Dmitry").withLastName("Aleksandrov").withAddress("Moscow street 1").withHomePhone("11111111").withMobilePhone("22222222")
              .withWorkPhone("33333333").withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com"));
    } else {
      int contactSize = contact.getGroups().size();
      Groups before = contact.getGroups();
      System.out.println(contactSize);
      if (!contact.getGroups().isEmpty()) {
        Groups groupsInContact = contact.getGroups();
        int groupsInContactSize = groupsInContact.size();
        while (groupsInContactSize > 0) {
          GroupData group = groupsInContact.iterator().next();
          app.contact().delete(group);
          groupsInContactSize--;
        }
      } else {
        app.contact().returnToHomePage();
        app.contact().selectContactById(contact.getId());
        app.contact().addToGroup(groups);
        app.contact().returnToHomePage();
      }
      int contactId = contact.getId();
      ContactData contactAdded = app.db().contact(contactId);
      Groups after = contactAdded.getGroups();
      GroupData group = after.iterator().next();
      System.out.println(contact.getGroups().size());
      assertThat(after, equalTo(
              before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
  }
}