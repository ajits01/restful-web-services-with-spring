package io.github.ajits01.restfulws.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import io.github.ajits01.restfulws.domain.User;
import io.github.ajits01.restfulws.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private static List<User> users = new ArrayList<>();
  private static int USERS_COUNT = 104;

  static {
    users.add(new User(100, "John", new Date(), null));
    users.add(new User(101, "Ron", new Date(), null));
    users.add(new User(102, "Robert", new Date(), null));
    users.add(new User(103, "Kristen", new Date(), null));
    users.add(new User(104, "Enrique", new Date(), null));
  }

  public Optional<User> findOne(int id) {
    for (User user : users) {
      if (user.getId() == id) {
        return Optional.of(user);
      }
    }
    return Optional.ofNullable(null);
  }

  public List<User> findAll() {
    return users;
  }

  public User save(User user) {
    if (user.getId() == null) {
      user.setId(++USERS_COUNT);
    }
    users.add(user);
    return user;
  }

  public Optional<User> deleteById(int id) {
    Iterator<User> iterator = users.iterator();
    while (iterator.hasNext()) {
      User user = iterator.next();
      if (user.getId() == id) {
        iterator.remove();
        return Optional.of(user);
      }
    }
    return Optional.ofNullable(null);
  }
}
