package ru.cs.vsu.ast2.api.auth.login.dto;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

public class LoginRequest {

    String phonePattern = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("phoneNumber")
    public String phoneNumber;

    public LoginRequest(String login, String password) {
        if (Pattern.compile(phonePattern).matcher(login).matches()) {
            this.phoneNumber = login;
        } else {
            this.email = login;
        }
        this.password = password;
    }

}
