package ru.cs.vsu.ast2.api.roles.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Role {

    private String description;
    private UUID id;
    private String name;
}