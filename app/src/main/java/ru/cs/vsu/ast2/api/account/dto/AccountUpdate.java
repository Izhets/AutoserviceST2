package ru.cs.vsu.ast2.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountUpdate {

    private String confirmPassword;
    private String name;
    private String password;
    private String surname;

}
