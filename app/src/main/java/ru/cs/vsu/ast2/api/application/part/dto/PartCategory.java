package ru.cs.vsu.ast2.api.application.part.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PartCategory {

    private UUID id;
    private String name;

}