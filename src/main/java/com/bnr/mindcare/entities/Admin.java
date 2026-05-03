package com.bnr.mindcare.entities;

public class Admin extends Person {

    private String adminCode;

    public Admin(String name, int age, String email, String adminCode) {
        super(name, age, email);
        this.adminCode = adminCode;
    }

    @Override
    public String getRole() { return "Admin"; }

    public String getAdminCode() { return adminCode; }
}