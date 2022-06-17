package ru.cs.vsu.ast2.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplenishBalance {

    private String paymentType;
    private Integer value;

}