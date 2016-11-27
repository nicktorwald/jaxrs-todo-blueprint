package org.nicktorwald.todo.service.impl;

import java.util.Collection;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.nicktorwald.todo.domain.Task;
import org.nicktorwald.todo.endpoint.command.ActivateTaskCommand;
import org.nicktorwald.todo.endpoint.command.AddNewTaskCommand;
import org.nicktorwald.todo.endpoint.command.ChangeTaskCommand;
import org.nicktorwald.todo.endpoint.command.CompleteTaskCommand;
import org.nicktorwald.todo.endpoint.command.RemoveAllTasksCommand;
import org.nicktorwald.todo.endpoint.command.RemoveTaskCommand;
import org.nicktorwald.todo.endpoint.query.GetTaskQuery;
import org.nicktorwald.todo.endpoint.query.ListTasksQuery;
import org.nicktorwald.todo.repository.TaskRepository;
import org.nicktorwald.todo.service.TaskService;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Inject
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public CompletionStage<Collection<Task>> listTasks(ListTasksQuery query) {
        return repository.listTasksByCompletionStatus(query.getCompleted());
    }

    @Override
    public CompletionStage<Task> getTask(GetTaskQuery query) {
        return repository.findOne(query.getTaskId())
                .thenApplyAsync(task -> task.orElseThrow(() -> new NotFoundException("Task not found")));
    }

    @Override
    public CompletionStage<Task> addTask(AddNewTaskCommand command) {
        return repository.store(Task.newActiveTask(command.getTitle()));
    }

    @Override
    public CompletionStage<Task> changeTask(ChangeTaskCommand command) {
        return repository.findOne(command.getTaskId())
                .thenApplyAsync(task -> task.orElseThrow(() -> new NotFoundException("Task not found")))
                .thenApply(task -> task.changeTitle(command.getPayload().getTitle()))
                .thenComposeAsync(repository::store);
    }

    @Override
    public CompletionStage<Task> completeTask(CompleteTaskCommand command) {
        return repository.findOne(command.getTaskId())
                .thenApplyAsync(task -> task.orElseThrow(() -> new NotFoundException("Task not found")))
                .thenApply(task -> task.complete())
                .thenComposeAsync(repository::store);
    }

    @Override
    public CompletionStage<Task> activateTask(ActivateTaskCommand command) {
        return repository.findOne(command.getTaskId())
                .thenApplyAsync(task -> task.orElseThrow(() -> new NotFoundException("Task not found")))
                .thenApply(task -> task.activate())
                .thenComposeAsync(repository::store);
    }

    @Override
    public CompletionStage<Void> removeTask(RemoveTaskCommand command) {
        return repository.remove(command.getTaskId());
    }

    @Override
    public CompletionStage<Void> removeAllTask(RemoveAllTasksCommand command) {
        return repository.removeAll();
    }

}
