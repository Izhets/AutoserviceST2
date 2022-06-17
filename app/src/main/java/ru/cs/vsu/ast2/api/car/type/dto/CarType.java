package ru.cs.vsu.ast2.api.car.type.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CarType {

    private UUID id;
    private String type;

}
