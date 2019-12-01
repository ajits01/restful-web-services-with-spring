package io.github.ajits01.restfulws.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.github.ajits01.restfulws.domain.DeleteStatus;
import io.github.ajits01.restfulws.domain.User;
import io.github.ajits01.restfulws.exception.UserNotFoundException;
import io.github.ajits01.restfulws.service.impl.UserServiceImpl;

@RestController
public class UserController {

  @Autowired private UserServiceImpl userService;

  @GetMapping(path = "/users/{id}")
  public EntityModel<User> retrieveUserById(@PathVariable(name = "id") int userId) {
    Optional<User> retrievedUser = userService.findOne(userId);
    if (retrievedUser.isPresent()) {
      User user = retrievedUser.get();
      
      List<Link> links = new ArrayList<>();
      links.add(linkTo(methodOn(getClass()).retrieveUserById(user.getId())).withSelfRel());
      links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withRel("all_users"));
      
      EntityModel<User> userResource = new EntityModel<User>(user, links);
      return userResource;
    } else {
      throw new UserNotFoundException("USER NOT FOUND");
    }
  }

  @GetMapping(path = "/users")
  public CollectionModel<User> retrieveAllUsers() {
    List<User> allUsers = userService.findAll();
    
    List<Link> links = new ArrayList<>();
    links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withSelfRel());
    
    return new CollectionModel<>(allUsers, links);
  }

  @PostMapping(path = "/users")
  public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
    User savedUser = userService.save(user);

    List<Link> links = new ArrayList<>();
    links.add(linkTo(methodOn(getClass()).retrieveUserById(savedUser.getId())).withSelfRel());
    links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withRel("all_users"));

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(linkTo(getClass()).slash(savedUser.getId()).toUri());
    /*
        URI location =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{newId}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        ResponseEntity<User> response = ResponseEntity.created(location).body(savedUser);
    */
    return new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/users/{id}")
  public EntityModel<DeleteStatus> deleteUser(@PathVariable int id) {
    Optional<User> deletedUser = userService.deleteById(id);

    List<Link> links = new ArrayList<>();
    links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withRel("all_users"));

    if (!deletedUser.isPresent()) {
      throw new UserNotFoundException("USER NOT FOUND");
    }

    return new EntityModel<>(new DeleteStatus("DELETED"), links);
  }
}
