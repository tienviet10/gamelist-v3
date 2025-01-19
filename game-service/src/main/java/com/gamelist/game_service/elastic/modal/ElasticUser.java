package com.gamelist.game_service.elastic.modal;

import java.time.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@Document(indexName = "users")
public class ElasticUser {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private LocalDateTime createDate;
}
