package org.nicktorwald.todo.domain;

import org.mongodb.morphia.annotations.Entity;

@Entity("tasks")
public class Task extends BaseEntity {
    
    public static final String TITLE_FIELD = "isCompleted";
    public static final String COMPLETED_FIELD = "isCompleted";
    
    private String title;
    private Boolean isCompleted;

    Task() {
    }

    private Task(String title) {
        this.title = title;
    }
    
    public static Task newActiveTask(String title) {        
        return new Task(title).activate();
    }
    
    public Task complete() {
        isCompleted = true;
        return this;
    }
    
    public Task activate() {
        isCompleted = false;
        return this;
    }
    
    public Task changeTitle(String title) {
        this.title = title;
        return this;
    }
    
    public String getTitle() {
        return title;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }
    
}
