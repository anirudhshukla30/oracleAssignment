package business;

import java.util.*;
import java.util.stream.Collectors;

import data.Entity;
import data.Project;

public class EntityAggregator {

    // entityMap holds an entity ID vs a collection of Entities. The collection of
    // Entities here is stored as a Map instead of a Set or List to make it easier
    // to handle the case of distinguishing 2 objects having same ID. <Cleaner to
    // implement rather than overriding equals and hashcode for comparing Set of
    // Objects>
    private Map<Long, Map<Long, Entity>> entityMap = new HashMap<Long, Map<Long, Entity>>();

    // This is used to calculare average Build duration
    private long totalBuildTime = 0;

    /**
     * This method takes in an entity ID and stores the collection of Entities
     * against it. As evident below, if in case the Collection is new, it is
     * initialized, else the collection of entities aggregated against the entity
     * denoted by entityID are first collected into a Map and then the new Additions
     * are put in accordingly
     *
     * @param entityId
     * @param entity
     */
    public void init(Long entityId, Entity entity) {

        if (!this.entityMap.containsKey(entityId)) {
            Map<Long, Entity> entities = new HashMap<Long, Entity>();
            entities.put(entity.getId(), entity);
            entityMap.put(entityId, entities);
        } else {
            Map<Long, Entity> entities = new HashMap<Long, Entity>();
            if (!(entityMap.get(entityId)).containsKey(entity.getId())) {
                entities = entityMap.get(entityId);
                entities.put(entity.getId(), entity);
            }
            entityMap.put(entityId, entities);
        }
    }

    /**
     * Returns a Set of Unique Entities for getting list of customers against
     * geozones, contracts etc.
     *
     * @param entityId
     * @return
     */
    public Set<Entity> getUniqueEntities(Long entityId) {
        Set<Entity> uniqueEntities = new HashSet<Entity>();
        entityMap.get(entityId).forEach((k, v) -> uniqueEntities.add(v));
        return uniqueEntities;
    }

    /**
     * Returns the size of the Set returned by getUniqueEntities method i.e. the
     * size of the aggregate entities against a said Entity. E.g. customers against
     * contract etc.
     *
     * @param entityId
     * @return
     */
    public int getUniqueCount(Long entityId) {
        return this.getUniqueEntities(entityId).size();
    }

    /**
     * To return the Average build duration of the Geozones, an entity Project is
     * utilized to associate the attribute build duration as it would match a real
     * world scenario. In case the build duration is not bearing any association
     * with a Project then it can be worked on independently
     *
     * @param entityId
     * @return
     */
    public long average(Long entityId) {
        this.totalBuildTime = 0;
        for (Entity e : this.getUniqueEntities(entityId)) {
            this.totalBuildTime += ((Project) e).getBuildDuration();
        }

        return this.totalBuildTime / (this.getUniqueCount(entityId));
    }

}
