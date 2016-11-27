package org.nicktorwald.todo.repository;

import java.util.Collection;
import java.util.concurrent.CompletionStage;
import org.nicktorwald.todo.domain.Task;

public interface TaskRepository extends Repository<Task, String> {
    
    CompletionStage<Collection<Task>> listTasksByCompletionStatus(Boolean completed);
    
}
