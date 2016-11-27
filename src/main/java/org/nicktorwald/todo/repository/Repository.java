package org.nicktorwald.todo.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import org.nicktorwald.todo.domain.BaseEntity;

public interface Repository<T extends BaseEntity, ID> {
    
    CompletionStage<T> store(T entity);
    
    CompletionStage<Optional<T>> findOne(ID id);
    
    CompletionStage<Collection<T>> findAll();
    
    CompletionStage<Void> remove(ID id);
    
    CompletionStage<Void> remove(T entity);
    
    CompletionStage<Void> removeAll();
    
}
