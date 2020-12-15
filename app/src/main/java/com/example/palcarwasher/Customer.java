package com.example.palcarwasher;

import android.provider.ContactsContract;

import java.net.PasswordAuthentication;
import java.util.Date;

public class Customer {


  private String customerId;
  private String name;
  private String phoneNumber;
  private String password;
  private String email;
  private String birthday;
  private String gender;
  private String profilePic;

    public Customer() {
    }

    public Customer(String customerId, String name, String phoneNumber, String password, String email, String birthday, String gender) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Customer(String customerId, String name, String phoneNumber, String password, String email, String birthday, String gender, String profilePic) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.profilePic = profilePic;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public  String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}//class Customer






