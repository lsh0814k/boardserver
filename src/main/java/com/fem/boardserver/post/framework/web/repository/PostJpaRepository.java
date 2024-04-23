package com.fem.boardserver.post.framework.web.repository;

import com.fem.boardserver.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(String userId);

    Optional<Post> findByIdAndUserId(Long postId, String userId);
}
