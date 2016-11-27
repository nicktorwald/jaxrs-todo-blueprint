package org.nicktorwald.todo.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class BaseEntity {
    
    @Id
    private String id = new ObjectId().toString();

    public String getId() {
        return id;
    }
    
}
