package ru.cs.vsu.ast2.api.car.brand.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CarModel {

    private UUID id;
    private String name;
    private CarBrand brand;

}
