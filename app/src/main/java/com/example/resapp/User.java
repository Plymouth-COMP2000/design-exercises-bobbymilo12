package com.example.resapp;

public class User {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String usertype;

    public User(String username,
                String password,
                String firstname,
                String lastname,
                String email,
                String contact,
                String usertype) {

        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.usertype = usertype;
    }
}
