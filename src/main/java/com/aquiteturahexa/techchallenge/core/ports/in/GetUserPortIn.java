package com.aquiteturahexa.techchallenge.core.ports.in;

import com.aquiteturahexa.techchallenge.core.model.User;

import java.util.Optional;

public interface GetUserPortIn {

    Optional<User> getUser(String username);

    Optional<User> getUser(String username, String password);
}
