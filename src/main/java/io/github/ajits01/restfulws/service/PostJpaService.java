package io.github.ajits01.restfulws.service;

import java.util.List;
import io.github.ajits01.restfulws.domain.Post;

public interface PostJpaService {

  public List<Post> findPostByUserId();

  public Post createPost(Post post);
}
