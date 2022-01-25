package ru.stqa.pft.addressbook.tests.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.tests.model.ContactData;
import ru.stqa.pft.addressbook.tests.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/stru.png");
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while(line != null) {
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2]).withHomePhone(split[3]).withHomePhone2(split[4]).withMobilePhone(split[5]).withWorkPhone(split[6]).withEmail(split[7]).withEmail2(split[8]).withEmail3(split[9]).withPhoto(photo)});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    String[] names = new String[] {"test1'", "test2", "test3"};
    app.goTo().contactPage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded( contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation()  {
    app.goTo().contactPage();
    File photo = new File("src/test/resources/stru.png");
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Dmitry'").withLastName("Aleksandrov").withAddress("Moscow street 1").withHomePhone("11111111").withMobilePhone("22222222").withWorkPhone("33333333").withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withPhoto(photo);
    app.contact().initContactCreation();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}
