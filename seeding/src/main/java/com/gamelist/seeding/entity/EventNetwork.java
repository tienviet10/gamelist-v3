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
@Entity(name = "event_networks")
public class EventNetwork {

    @Id
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToOne
    @JoinColumn(name = "network_type_id", nullable = false)
    private NetworkType networkType;

    @OneToOne
    @JoinColumn(nullable = false)
    private Event event;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
