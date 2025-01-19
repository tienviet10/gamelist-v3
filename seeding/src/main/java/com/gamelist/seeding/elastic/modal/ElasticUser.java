package com.gamelist.seeding.elastic.modal;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.*;

@Data
@Document(indexName = "users")
public class ElasticUser {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private LocalDateTime createDate;
}
