package ru.cs.vsu.ast2.api.news.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class News {

    private UUID id;
    private String title;
    private String content;
    private String imgUrl;

}
