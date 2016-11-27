package org.nicktorwald.todo.endpoint.query;

import javax.ws.rs.PathParam;

public class GetTaskQuery {

    @PathParam("taskId")
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

}
