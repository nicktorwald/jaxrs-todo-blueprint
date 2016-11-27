package org.nicktorwald.todo.endpoint.command;

import javax.validation.constraints.NotNull;

public class AddNewTaskCommand {

    @NotNull
    private String title;

    public String getTitle() {
        return title;
    }

}
