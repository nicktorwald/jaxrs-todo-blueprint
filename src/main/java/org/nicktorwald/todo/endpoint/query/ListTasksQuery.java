package org.nicktorwald.todo.endpoint.query;

import javax.ws.rs.QueryParam;

public class ListTasksQuery {
    
    @QueryParam("completed")
    private Boolean completed;

    public Boolean getCompleted() {
        return completed;
    }

    
    
}
