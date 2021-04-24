package com.example.Market.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "Clients")
public class Client {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private int account_number;
    private String password;
    private String validationToken;
    private String mail;
    private boolean isActive;
    private boolean loggedIn;
    private Date loginExparDate;


    public Client(long id, String firstName, String lastName, int account_number, String mail , String validationToken, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account_number = account_number;
        this.validationToken = validationToken;
        this.mail = mail;
        this.isActive = isActive;
        this.loggedIn = false;
        this.password= "";
    }

    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() { return loggedIn; }

    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public Date getLoginExparDate() { return loginExparDate; }

    public void setLoginExparDate(Date lastLogDate) { this.loginExparDate = lastLogDate; }

}
