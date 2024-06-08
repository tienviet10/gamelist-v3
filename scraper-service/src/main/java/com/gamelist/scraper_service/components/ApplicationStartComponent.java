package com.gamelist.scraper_service.components;

import org.springframework.boot.context.event.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

@Component
public class ApplicationStartComponent implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // TODO: Connect to web IGDB web hook
    }
}