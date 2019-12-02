package io.github.ajits01.restfulws.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import io.github.ajits01.restfulws.domain.Post;
import io.github.ajits01.restfulws.domain.User;
import io.github.ajits01.restfulws.exception.UserNotFoundException;
import io.github.ajits01.restfulws.service.impl.PostJpaServiceImpl;
import io.github.ajits01.restfulws.service.impl.UserJpaServiceImpl;

@RestController
public class UserJpaController {

  @Autowired private UserJpaServiceImpl userJpaService;
  @Autowired private PostJpaServiceImpl postJpaService;

  @GetMapping(path = "/jpa/users/{id}")
  public EntityModel<User> retrieveUserById(@PathVariable(name = "id") int userId) {
    Optional<User> retrievedUser = userJpaService.findOne(userId);
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

  @GetMapping(path = "/jpa/users")
  public CollectionModel<User> retrieveAllUsers() {
    List<User> allUsers = userJpaService.findAll();

    List<Link> links = new ArrayList<>();
    links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withSelfRel());

    return new CollectionModel<>(allUsers, links);
  }

  @GetMapping(path = "/jpa/users/{id}/posts")
  public CollectionModel<Post> retrieveAllPostByUserId(@PathVariable int id) {

    Optional<User> retrievedUser = userJpaService.findOne(id);

    if (retrievedUser.isPresent()) {
      User user = retrievedUser.get();

      List<Post> posts = user.getPosts();

      List<Link> links = new ArrayList<>();
      links.add(linkTo(methodOn(getClass()).retrieveAllPostByUserId(user.getId())).withSelfRel());

      return new CollectionModel<>(posts, links);
    } else {
      throw new UserNotFoundException("USER NOT FOUND");
    }
  }

  @PostMapping(path = "/jpa/users")
  public ResponseEntity<User> createUser(@RequestBody @Valid User user) {

    User savedUser = userJpaService.save(user);

    List<Link> links = new ArrayList<>();
    links.add(linkTo(methodOn(getClass()).retrieveUserById(savedUser.getId())).withSelfRel());
    links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withRel("all_users"));

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(linkTo(getClass()).slash(savedUser.getId()).toUri());

    return new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
  }

  @PostMapping(path = "/jpa/users/{id}/posts")
  public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody @Valid Post post) {

    Optional<User> retrievedUser = userJpaService.findOne(id);

    if (retrievedUser.isPresent()) {
      User user = retrievedUser.get();

      post.setUser(user);
      Post createdPost = postJpaService.createPost(post);

      user.getPosts().add(createdPost);

      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(linkTo(getClass()).slash(createdPost.getId()).toUri());

      return new ResponseEntity<Post>(createdPost, headers, HttpStatus.CREATED);
    } else {
      throw new UserNotFoundException("USER NOT FOUND");
    }
  }

  @DeleteMapping(path = "/jpa/users/{id}")
  public EntityModel<DeleteStatus> deleteUser(@PathVariable int id) {

    try {
      userJpaService.deleteById(id);
    } catch (DataAccessException e) {
      throw new UserNotFoundException("USER NOT FOUND");
    }

    List<Link> links = new ArrayList<>();
    links.add(linkTo(methodOn(getClass()).retrieveAllUsers()).withRel("all_users"));

    return new EntityModel<>(new DeleteStatus("DELETED"), links);
  }
}
