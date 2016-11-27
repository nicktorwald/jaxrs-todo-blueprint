package org.nicktorwald.todo.repository;

import com.mongodb.MongoClient;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.nicktorwald.todo.domain.BaseEntity;

public abstract class CrudRepository<T extends BaseEntity, ID>
        extends BasicDAO<T, ID>
        implements Repository<T, ID> {

    private final ScheduledExecutorService executor;
    
    public CrudRepository(MongoClient mongoClient, 
                          Morphia morphia,
                          String dbName,
                          ScheduledExecutorService executor) {
        
        super(mongoClient, morphia, dbName);
        this.executor = executor;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }   
    
    @Override
    public CompletionStage<T> store(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            save(entity);
            return entity;
        }, executor);
    }

    @Override
    public CompletionStage<Optional<T>> findOne(ID id) {
        return CompletableFuture.supplyAsync(
                () -> Optional.ofNullable(get(id)),
                executor
        );
    }

    @Override
    public CompletionStage<Collection<T>> findAll() {
        return CompletableFuture.supplyAsync(
                () -> find().asList(),
                executor
        );
    }

    @Override
    public CompletionStage<Void> remove(ID id) {
        return CompletableFuture.runAsync(
                () -> deleteById(id),
                executor
        );
    }

    @Override
    public CompletionStage<Void> remove(T entity) {
        return CompletableFuture.runAsync(
                () -> delete(entity),
                executor
        );
    }

    @Override
    public CompletionStage<Void> removeAll() {
        return CompletableFuture.runAsync(
                () -> deleteByQuery(createQuery()),
                executor);
    }

}
