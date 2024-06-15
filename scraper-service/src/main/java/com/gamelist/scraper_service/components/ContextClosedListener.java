package com.gamelist.scraper_service.components;

import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

@Component
public class ContextClosedListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // TODO: Unregister web hook
    }
}