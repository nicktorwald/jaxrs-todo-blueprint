package org.nicktorwald.todo.endpoint.command;

import javax.ws.rs.PathParam;

public class CompleteTaskCommand {

    @PathParam("taskId")
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

}
