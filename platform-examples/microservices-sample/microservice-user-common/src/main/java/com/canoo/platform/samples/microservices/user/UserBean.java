package com.canoo.platform.samples.microservices.user;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.Property;

@DolphinBean
public class UserBean {

    private Property<String> name;

    private Property<String> firstName;

    private Property<String> lastName;

    private Property<String> mail;

    public String getName() {
        return name.get();
    }

    public Property<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public Property<String> firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public Property<String> lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMail() {
        return mail.get();
    }

    public Property<String> mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }
}
