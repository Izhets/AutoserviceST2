package ru.cs.vsu.ast2.api.application.part.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class Part {

    private UUID id;
    private String name;
    private PartCategory partCategory;

}