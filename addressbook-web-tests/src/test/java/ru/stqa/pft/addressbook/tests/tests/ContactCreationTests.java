package ru.stqa.pft.addressbook.tests.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.tests.model.ContactData;
import ru.stqa.pft.addressbook.tests.model.Contacts;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    File photo = new File("src/test/resources/stru.png");
     try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while(line != null) {
        xml+= line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.allowTypes(new Class[]{ ContactData.class });
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    File photo = new File("src/test/resources/stru.png");
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while(line != null) {
        json+= line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType()); // List<ContactData>.class
      return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test (dataProvider = "validContactsFromXml")
  public void testContactCreation(ContactData contact) {
    app.goTo().contactPage();
//    Contacts before = app.contact().all();
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
//    Contacts after = app.contact().all();
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded( contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    verifyContactListInUI();
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
