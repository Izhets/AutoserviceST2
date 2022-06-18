package ru.cs.vsu.ast2.api.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.cs.vsu.ast2.api.application.part.dto.Part;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Application {

    private UUID id;
    private String description;
    private String status;
    private Integer price;
    private UUID ownerId;
    private Part part;

}