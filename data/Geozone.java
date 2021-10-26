package data;

import java.util.*;

public class Geozone implements Entity {

    // Numerical ID for a Geo Zone
    private long id;
    // Zone value
    private String zone;

    // Since Geozone can hold an aggregate of customers
    private Set<Entity> entities;

    @Override
    public boolean addEntity(Entity entity) {
        return false;
    }

    @Override
    public Set<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

}
