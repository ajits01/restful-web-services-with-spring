package io.github.ajits01.restfulws.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.ajits01.restfulws.domain.User;
import io.github.ajits01.restfulws.repository.UserRepository;
import io.github.ajits01.restfulws.service.UserJpaService;

@Service
public class UserJpaServiceImpl implements UserJpaService {

  @Autowired private UserRepository userJpaRepository;

  public Optional<User> findOne(int id) {

    Optional<User> retrievedUser = userJpaRepository.findById(id);

    return retrievedUser;
  }

  public List<User> findAll() {

    return userJpaRepository.findAll();
  }

  public User save(User user) {

    User savedUser = userJpaRepository.save(user);
    return savedUser;
  }

  public void deleteById(int id) {

    userJpaRepository.deleteById(id);
  }
}
