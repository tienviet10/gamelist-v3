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
@Entity(name = "involved_companies")
public class InvolvedCompany {

    @Id
    private Long id;

    @Column(nullable = false)
    private boolean porting;

    @Column(nullable = false)
    private boolean publisher;

    @Column(nullable = false)
    private boolean supporting;

    @Column(nullable = false)
    private boolean developer;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @OneToOne
    private Company company;

    @ManyToOne
    private Game game;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
