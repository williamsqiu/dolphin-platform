package com.canoo.platform.server.security;

import java.util.Optional;
import java.util.stream.Stream;

public interface User {

    Stream<String> getRoles();

    String getEmail();

    default Optional<String> email() {
        return Optional.ofNullable(getEmail());
    }

    String getUserName();

    default Optional<String> userName() {
        return Optional.ofNullable(getUserName());
    }

    String getFirstName();

    default Optional<String> firstName() {
        return Optional.ofNullable(getFirstName());
    }

    String getLastName();

    default Optional<String> lastName() {
        return Optional.ofNullable(getLastName());
    }

}
