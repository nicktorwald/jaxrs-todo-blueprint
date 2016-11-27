package org.nicktorwald.todo.service;

import java.util.Collection;
import java.util.concurrent.CompletionStage;
import org.nicktorwald.todo.domain.Task;
import org.nicktorwald.todo.endpoint.command.ActivateTaskCommand;
import org.nicktorwald.todo.endpoint.command.AddNewTaskCommand;
import org.nicktorwald.todo.endpoint.command.ChangeTaskCommand;
import org.nicktorwald.todo.endpoint.command.CompleteTaskCommand;
import org.nicktorwald.todo.endpoint.command.RemoveAllTasksCommand;
import org.nicktorwald.todo.endpoint.command.RemoveTaskCommand;
import org.nicktorwald.todo.endpoint.query.GetTaskQuery;
import org.nicktorwald.todo.endpoint.query.ListTasksQuery;

public interface TaskService {
    
    CompletionStage<Collection<Task>> listTasks(ListTasksQuery query);
    
    CompletionStage<Task> getTask(GetTaskQuery query);
    
    CompletionStage<Task> addTask(AddNewTaskCommand command);
    
    CompletionStage<Task> changeTask(ChangeTaskCommand command);
    
    CompletionStage<Task> completeTask(CompleteTaskCommand command);
    
    CompletionStage<Task> activateTask(ActivateTaskCommand command);
    
    CompletionStage<Void> removeTask(RemoveTaskCommand command);
    
    CompletionStage<Void> removeAllTask(RemoveAllTasksCommand command);
    
}
