package org.nicktorwald.todo.inject.factory;

import org.glassfish.hk2.api.Factory;
import org.mongodb.morphia.Morphia;

public class MorphiaFactory implements Factory<Morphia> {

    @Override
    public Morphia provide() {
        return new Morphia();
    }

    @Override
    public void dispose(Morphia instance) {
        
    }
    
}
