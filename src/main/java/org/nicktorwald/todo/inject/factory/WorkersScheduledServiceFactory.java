package org.nicktorwald.todo.inject.factory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.glassfish.hk2.api.Factory;
import org.nicktorwald.todo.inject.Workers;

public class WorkersScheduledServiceFactory implements Factory<ScheduledExecutorService>{

    @Workers
    @Override
    public ScheduledExecutorService provide() {
        ThreadFactory factory = new ThreadFactoryBuilder()
                                        .setNameFormat("workers-pool-%d")
                                        .build();
        
        return Executors.newScheduledThreadPool(
                Runtime.getRuntime().availableProcessors() * 2,
                factory
        );
    }

    @Override
    public void dispose(ScheduledExecutorService instance) {
        instance.shutdown();
    }
    
}
