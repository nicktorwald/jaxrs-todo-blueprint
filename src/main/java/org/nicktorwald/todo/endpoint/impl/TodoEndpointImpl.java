package org.nicktorwald.todo.endpoint.impl;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.nicktorwald.todo.endpoint.TodoEndpoint;
import org.nicktorwald.todo.endpoint.command.ActivateTaskCommand;
import org.nicktorwald.todo.endpoint.command.AddNewTaskCommand;
import org.nicktorwald.todo.endpoint.command.ChangeTaskCommand;
import org.nicktorwald.todo.endpoint.command.CompleteTaskCommand;
import org.nicktorwald.todo.endpoint.command.RemoveAllTasksCommand;
import org.nicktorwald.todo.endpoint.command.RemoveTaskCommand;
import org.nicktorwald.todo.endpoint.query.GetTaskQuery;
import org.nicktorwald.todo.endpoint.query.ListTasksQuery;
import org.nicktorwald.todo.service.TaskService;

import static org.nicktorwald.todo.helper.CompletionStageHelper.*;

@Path("tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TodoEndpointImpl implements TodoEndpoint {

    TaskService service;

    @Inject
    public TodoEndpointImpl(TaskService service) {
        this.service = service;
    }
    
    @Override
    public void addTask(AsyncResponse response,
                        UriInfo uriInfo, 
                        AddNewTaskCommand command) {
        service.addTask(command)
                .thenApply(task -> 
                    response.resume(
                            Response.created(
                                    uriInfo.getAbsolutePathBuilder().path(task.getId()).build()
                            ).build()
                    )
                )
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void listTasks(AsyncResponse response, ListTasksQuery query) {
        service.listTasks(query)
                .thenApply(response::resume)
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void getTask(AsyncResponse response, GetTaskQuery query) {
        service.getTask(query)
                .thenApply(response::resume)
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void changeTask(AsyncResponse response,
                           ChangeTaskCommand command,
                           ChangeTaskCommand.Payload payload) {
        service.changeTask(command.withPayload(payload))
                .thenApply(response::resume)
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void completeTask(AsyncResponse response,
                             CompleteTaskCommand command) {
        service.completeTask(command)
                .thenApply(task -> response.resume(Response.noContent().build()))
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void activateTask(AsyncResponse response,
                             ActivateTaskCommand command) {
        service.activateTask(command)
                .thenApply(task -> response.resume(Response.noContent().build()))
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void removeTask(AsyncResponse response,
                           RemoveTaskCommand command) {
        service.removeTask(command)
                .thenApply(task -> response.resume(Response.noContent().build()))
                .exceptionally(unfold(response::resume));
    }

    @Override
    public void removeAllTask(AsyncResponse response,
                              RemoveAllTasksCommand command) {
        service.removeAllTask(command)
                .thenApply(task -> response.resume(Response.noContent().build()))
                .exceptionally(unfold(response::resume));
    }

}
