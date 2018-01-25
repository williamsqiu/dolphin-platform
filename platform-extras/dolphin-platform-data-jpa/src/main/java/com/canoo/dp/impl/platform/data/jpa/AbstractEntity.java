package com.canoo.dp.impl.platform.data.jpa;


import com.canoo.dp.impl.platform.data.EntityWithId;
import com.canoo.dp.impl.platform.data.jpa.event.PersistenceListenerManager;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Version;

@MappedSuperclass
public class AbstractEntity implements EntityWithId<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Override
    public final Long getId() {
        return id;
    }

    @Override
    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the version
     *
     * @return the version
     */
    public final Long getVersion() {
        return version;
    }

    /**
     * Set the version
     *
     * @param version the version
     */
    public final void setVersion(Long version) {
        this.version = version;
    }

    @PostPersist
    public void onPersist() {
        PersistenceListenerManager.getInstance().firePersisted(this);
    }

    @PostRemove
    public void onRemove() {
        PersistenceListenerManager.getInstance().fireRemoved(this);
    }

    @PostUpdate
    public void onUpdate() {
        PersistenceListenerManager.getInstance().fireUpdated(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
