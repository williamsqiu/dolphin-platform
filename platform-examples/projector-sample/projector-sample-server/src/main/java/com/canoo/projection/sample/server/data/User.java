package com.canoo.projection.sample.server.data;

import com.canoo.dp.impl.platform.data.EntityWithId;

public class User implements EntityWithId<String> {

    private String id;

    private String firstName;

    private String lastName;

    private boolean active;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }
}
