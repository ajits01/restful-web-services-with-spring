package io.github.ajits01.restfulws.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.github.ajits01.restfulws.domain.User;
import io.github.ajits01.restfulws.exception.UserNotFoundException;
import io.github.ajits01.restfulws.service.impl.UserServiceImpl;

@RestController
public class UserController {

  @Autowired private UserServiceImpl userService;

  @GetMapping(path = "/users/{id}")
  public Optional<User> retrieveUserById(@PathVariable(name = "id") int userId) {
    Optional<User> retrievedUser = userService.findOne(userId);
    if (retrievedUser.isPresent()) {
      return retrievedUser;
    } else {
      throw new UserNotFoundException("USER NOT FOUND");
    }
  }

  @GetMapping(path = "/users")
  public List<User> retrieveAllUsers() {
    return userService.findAll();
  }

  @PostMapping(path = "/users")
  public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
    User savedUser = userService.save(user);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{newId}")
            .buildAndExpand(savedUser.getId())
            .toUri();
    return ResponseEntity.created(location).body(savedUser);
  }

  @DeleteMapping(path = "/users/{id}")
  public void deleteUser(@PathVariable int id) {
    Optional<User> deletedUser = userService.deleteById(id);

    if (!deletedUser.isPresent()) {
      throw new UserNotFoundException("USER NOT FOUND");
    }
  }
}
