package io.github.ajits01.restfulws.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.ajits01.restfulws.domain.Post;
import io.github.ajits01.restfulws.repository.PostJpaRepository;
import io.github.ajits01.restfulws.service.PostJpaService;

@Service
public class PostJpaServiceImpl implements PostJpaService {

  @Autowired private PostJpaRepository postJpaRepository;

  public List<Post> findPostByUserId() {

    return postJpaRepository.findAll();
  }

  public Post createPost(Post post) {

    return postJpaRepository.save(post);
  }
}
