package ru.cs.vsu.ast2.api.auth.register.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("confirmPassword")
    public String confirmPassword;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;
    @SerializedName("password")
    public String password;
    @SerializedName("phone")
    public String phone;
    @SerializedName("surname")
    public String surname;

    public RegisterRequest(String confirmPassword, String email, String name, String password, String phone, String surname) {
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.surname = surname;
    }

}