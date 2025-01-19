package com.gamelist.seeding.elastic.modal;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.*;
import java.util.*;

@Data
@Document(indexName = "games")
public class ElasticGame {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long gameId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private double averageRating;

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Date)
    private LocalDateTime releaseDate;
}
