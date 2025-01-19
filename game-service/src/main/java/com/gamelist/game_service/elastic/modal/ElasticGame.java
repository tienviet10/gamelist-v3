package com.gamelist.game_service.elastic.modal;

import java.time.*;
import java.util.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@Document(indexName = "games")
public class ElasticGame {

    @Id
    private String id;

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
