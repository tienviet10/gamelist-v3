package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "event_logos")
public class EventLogo {

    @Id
    private Long id;

    private boolean animated;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private String url;

    @Column(name = "alpha_channel")
    private boolean alphaChannel;

    @Column(name = "image_id", nullable = false)
    private String imageId;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToOne
    @JoinColumn(nullable = false)
    private Event event;
}
