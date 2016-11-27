package org.nicktorwald.todo.config;

import com.mongodb.MongoClient;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.mongodb.morphia.Morphia;
import org.nicktorwald.todo.inject.factory.MongoClientFactory;
import org.nicktorwald.todo.inject.NamedConstants;
import org.nicktorwald.todo.inject.Workers;
import org.nicktorwald.todo.inject.factory.MorphiaFactory;
import org.nicktorwald.todo.inject.factory.WorkersScheduledServiceFactory;
import org.nicktorwald.todo.repository.TaskRepository;
import org.nicktorwald.todo.repository.impl.TaskRepositoryImpl;
import org.nicktorwald.todo.service.TaskService;
import org.nicktorwald.todo.service.impl.TaskServiceImpl;

public class Application extends ResourceConfig {
    
    public Application() {
        initResources();
        initBindings();
    }

    private void initResources() {
        packages(
                "org.nicktorwald.todo.config",
                "org.nicktorwald.todo.endpoint.impl"
        );
    }
    
    private void initBindings() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind("store")
                        .to(String.class)
                        .named(NamedConstants.MONGO_URL);
                
                bind("todo")
                        .to(String.class)
                        .named(NamedConstants.MONGO_DATABASE);
                
                bindFactory(MongoClientFactory.class)
                        .to(MongoClient.class)
                        .in(Singleton.class);
                
                bindFactory(MorphiaFactory.class)
                        .to(Morphia.class)
                        .in(Singleton.class);
                
                bindFactory(WorkersScheduledServiceFactory.class)
                        .to(ScheduledExecutorService.class)
                        .qualifiedBy(Workers.Factory.get())
                        .in(Singleton.class);
                
                bind(TaskServiceImpl.class)
                        .to(TaskService.class)
                        .in(Singleton.class);
                
                bind(TaskRepositoryImpl.class)
                        .to(TaskRepository.class)
                        .in(Singleton.class);
            }
        });
    }

}
