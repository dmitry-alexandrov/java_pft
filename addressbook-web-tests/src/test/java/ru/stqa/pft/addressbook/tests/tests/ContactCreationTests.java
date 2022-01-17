package ru.stqa.pft.addressbook.tests.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.tests.model.ContactData;
import ru.stqa.pft.addressbook.tests.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation()  {
    app.goTo().contactPage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Dmitry").withLastName("Aleksandrov").withAddress("Moscow street 1").withHomePhone("11111111").withMobilePhone("22222222").withWorkPhone("33333333").withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withGroup("test1");
    app.contact().initContactCreation();
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation()  {
    app.goTo().contactPage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Dmitry'").withLastName("Aleksandrov").withAddress("Moscow street 1").withHomePhone("11111111").withMobilePhone("22222222").withWorkPhone("33333333").withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withGroup("test1");
    app.contact().initContactCreation();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}
