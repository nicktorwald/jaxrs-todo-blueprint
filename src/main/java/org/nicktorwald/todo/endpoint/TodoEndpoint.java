package org.nicktorwald.todo.endpoint;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.nicktorwald.todo.config.PATCH;
import org.nicktorwald.todo.endpoint.command.ActivateTaskCommand;
import org.nicktorwald.todo.endpoint.command.AddNewTaskCommand;
import org.nicktorwald.todo.endpoint.command.ChangeTaskCommand;
import org.nicktorwald.todo.endpoint.command.CompleteTaskCommand;
import org.nicktorwald.todo.endpoint.command.RemoveAllTasksCommand;
import org.nicktorwald.todo.endpoint.command.RemoveTaskCommand;
import org.nicktorwald.todo.endpoint.query.GetTaskQuery;
import org.nicktorwald.todo.endpoint.query.ListTasksQuery;

public interface TodoEndpoint {

    @POST
    void addTask(@Suspended AsyncResponse response,
                 @Context UriInfo uriInfo,
                 @Valid AddNewTaskCommand command);

    @GET
    void listTasks(@Suspended AsyncResponse response,
                   @Valid @BeanParam ListTasksQuery query);

    @GET
    @Path("{taskId}")
    void getTask(@Suspended AsyncResponse response,
                 @Valid @BeanParam GetTaskQuery query);

    @PATCH
    @Path("{taskId}")
    void changeTask(@Suspended AsyncResponse response,
                    @Valid @BeanParam ChangeTaskCommand command, 
                    @Valid ChangeTaskCommand.Payload payload);

    @PUT
    @Path("{taskId}/completions")
    void completeTask(@Suspended AsyncResponse response,
                      @Valid @BeanParam CompleteTaskCommand command);

    @DELETE
    @Path("{taskId}/completions")
    void activateTask(@Suspended AsyncResponse response,
                      @Valid @BeanParam ActivateTaskCommand command);

    @DELETE
    @Path("{taskId}")
    void removeTask(@Suspended AsyncResponse response,
                    @Valid @BeanParam RemoveTaskCommand command);

    @DELETE
    void removeAllTask(@Suspended AsyncResponse response,
                       @Valid @BeanParam RemoveAllTasksCommand command);

}
