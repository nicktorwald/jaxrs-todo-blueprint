package org.nicktorwald.todo.endpoint.command;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;

public class ChangeTaskCommand {

    @PathParam("taskId")
    private String taskId;

    private Payload payload;

    public String getTaskId() {
        return taskId;
    }

    public Payload getPayload() {
        return payload;
    }

    public ChangeTaskCommand withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }
    
    public static class Payload {
        
        @NotNull
        private String title;

        public String getTitle() {
            return title;
        }
        
    }

}
