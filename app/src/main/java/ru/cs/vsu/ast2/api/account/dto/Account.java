package ru.cs.vsu.ast2.api.account.dto;

import lombok.Getter;
import lombok.Setter;
import ru.cs.vsu.ast2.api.roles.dto.Role;

import java.util.UUID;

@Getter
@Setter
public class Account {

    private String email;
    private UUID id;
    private Integer money;
    private String name;
    private String phone;
    private Role role;
    private String surname;
}
