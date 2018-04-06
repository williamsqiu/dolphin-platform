package com.canoo.projection.sample.server.data;

import com.canoo.dp.impl.platform.data.CrudService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements CrudService<String, User> {

    @Override
    public User createNewInstance() {
        return new User();
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }

    @Override
    public User findById(final String s) {
        return null;
    }

    @Override
    public Optional<User> byId(final String s) {
        return Optional.empty();
    }

    @Override
    public Class<User> getDataClass() {
        return User.class;
    }

    @Override
    public User reset(final User entity) {
        entity.setActive(false);
        entity.setLastName(null);
        entity.setFirstName(null);
        return entity;
    }

    @Override
    public User save(final User toSave) {
        toSave.setId(UUID.randomUUID().toString());
        return toSave;
    }

    @Override
    public void delete(final User toDelete) {}
}
