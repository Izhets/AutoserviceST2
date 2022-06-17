package ru.cs.vsu.ast2.api.car.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.cs.vsu.ast2.api.car.brand.dto.CarModel;
import ru.cs.vsu.ast2.api.car.type.dto.CarType;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Car {

    private UUID id;
    private CarModel model;
    private String name;
    private String number;
    private UUID ownerId;
    private CarType type;

}
