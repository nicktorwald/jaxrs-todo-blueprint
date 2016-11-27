package org.nicktorwald.todo.inject.factory;

import com.mongodb.MongoClient;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Factory;
import org.nicktorwald.todo.inject.NamedConstants;

public class MongoClientFactory implements Factory<MongoClient> {

    private String mongoUrl;

    @Inject
    public MongoClientFactory(@Named(NamedConstants.MONGO_URL) String mongoUrl) {
        this.mongoUrl = mongoUrl;
    }
    
    @Override
    public MongoClient provide() {
        return new MongoClient(mongoUrl);
    }

    @Override
    public void dispose(MongoClient instance) {
        instance.close();
    }
    
}
