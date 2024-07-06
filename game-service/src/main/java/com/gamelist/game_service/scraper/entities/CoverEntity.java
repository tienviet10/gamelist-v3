package com.gamelist.game_service.scraper.entities;

import lombok.*;

@ToString
public class CoverEntity {

    private int id;
    private boolean alpha_channel;
    private boolean animated;
    private int game;
    private int height;
    private String image_id;
    private String url;
    private int width;
    private String checksum;

}
