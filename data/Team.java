package data;

import java.util.Set;

public class Team implements Entity {

    private long id;

    @Override
    public boolean addEntity(Entity entity) {
        return false;
    }

    @Override
    public Set<Entity> getEntities() {
        return null;
    }

    // @Override
    // public void aggregateOverEntities() {

    // }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
