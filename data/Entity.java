package data;

import java.util.Set;

/**
 * Interface to incorporate Different entities such as Customer, Team, GeoZone
 * etc. Each entity should be allowed to hold an aggregation of entities
 */
public interface Entity {

    public long getId();

    public boolean addEntity(Entity entity);

    public Set<Entity> getEntities();

}