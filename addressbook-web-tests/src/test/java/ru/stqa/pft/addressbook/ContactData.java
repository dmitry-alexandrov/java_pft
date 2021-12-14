package ru.stqa.pft.addressbook;

public class ContactData {
  private final String firstName;
  private final String lastName;
  private final String address;
  private final String phone1;
  private final String phone2;
  private final String email1;
  private final String email2;

  public ContactData(String firstName, String lastName, String address, String phone1, String phone2,
                     String email1, String email2) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.phone1 = phone1;
    this.phone2 = phone2;
    this.email1 = email1;
    this.email2 = email2;
  }

  public  String getFirstName() { return firstName; }

  public  String getLastName() { return lastName; }

  public  String getAddress() { return address; }

  public  String getPhone1() { return phone1; }

  public  String getPhone2() { return phone2; }

  public  String getEmail1() { return email1; }

  public  String getEmail2() { return email2; }
}