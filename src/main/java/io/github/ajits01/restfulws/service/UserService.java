package io.github.ajits01.restfulws.service;

import java.util.List;
import java.util.Optional;
import io.github.ajits01.restfulws.domain.User;

public interface UserService {

  public Optional<User> findOne(int id);

  public List<User> findAll();

  public User save(User user);
}
