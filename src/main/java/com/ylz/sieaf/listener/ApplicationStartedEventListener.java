package com.ylz.sieaf.listener;

import com.ylz.sieaf.core.util.SieafVersionTool;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("The Sieaf update's root dir is locate at:" + SieafVersionTool.getRootdir());
    }
}
