package data;

import java.util.Set;

public class Project implements Entity {

    private long id;
    // Assumption made - Build duration times should be associated with projects. If
    // in case they are not, these will be independently fetched entities
    private long buildDuration;

    @Override
    public boolean addEntity(Entity entity) {
        return false;
    }

    @Override
    public Set<Entity> getEntities() {
        return null;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public long getBuildDuration() {
        return buildDuration;
    }

    public void setBuildDuration(long buildDuration) {
        this.buildDuration = buildDuration;
    }

    public void setId(long id) {
        this.id = id;
    }

}
