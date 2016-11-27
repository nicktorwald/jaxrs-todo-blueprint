package org.nicktorwald.todo.repository.impl;

import com.mongodb.MongoClient;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import javax.inject.Named;
import org.mongodb.morphia.Morphia;
import org.nicktorwald.todo.domain.Task;
import org.nicktorwald.todo.inject.NamedConstants;
import org.nicktorwald.todo.inject.Workers;
import org.nicktorwald.todo.repository.CrudRepository;
import org.nicktorwald.todo.repository.TaskRepository;

public class TaskRepositoryImpl
        extends CrudRepository<Task, String>
        implements TaskRepository {

    @Inject
    public TaskRepositoryImpl(MongoClient mongoClient,
            Morphia morphia,
            @Named(NamedConstants.MONGO_DATABASE) String dbName,
            @Workers ScheduledExecutorService executor) {
        super(mongoClient, morphia, dbName, executor);
    }

    @Override
    public CompletionStage<Collection<Task>> listTasksByCompletionStatus(Boolean completed) {
        if (completed == null) {
            return findAll();
        }
        
        return CompletableFuture.supplyAsync(
                () -> find(createQuery()
                        .field(Task.COMPLETED_FIELD).equal(completed)
                ).asList(),
                getExecutor()
        );
    }

}
