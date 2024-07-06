package com.gamelist.seeding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "multiplayer_modes")
public class MultiplayerMode {

    @Id
    private Long id;

    @Column(name = "offline_coop_max")
    private int offlineCoopMax;

    @Column(name = "offline_max")
    private int offlineMax;

    @Column(name = "online_coop_max")
    private int onlineCoopMax;

    @Column(name = "online_max")
    private int onlineMax;

    @Column(name = "campaign_coop", nullable = false)
    private boolean campaignCoop;

    @Column(name = "drop_in", nullable = false)
    private boolean dropIn;

    @Column(name = "lan_coop", nullable = false)
    private boolean lanCoop;

    @Column(name = "offline_coop", nullable = false)
    private boolean offlineCoop;

    @Column(name = "online_coop", nullable = false)
    private boolean onlineCoop;

    @Column(name = "split_screen", nullable = false)
    private boolean splitScreen;

    @Column(name = "split_screen_online", nullable = false)
    private boolean splitScreenOnline;

    @Column(unique = true, nullable = false)
    private UUID checksum;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;
}
