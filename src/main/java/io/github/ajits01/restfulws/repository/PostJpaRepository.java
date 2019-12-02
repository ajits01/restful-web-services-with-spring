package io.github.ajits01.restfulws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.ajits01.restfulws.domain.Post;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Integer> {}
