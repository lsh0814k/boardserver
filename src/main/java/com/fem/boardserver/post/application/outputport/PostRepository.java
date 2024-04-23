package com.fem.boardserver.post.application.outputport;

import com.fem.boardserver.post.domain.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    Post getById(Long postId);

    void delete(Post post);

    List<Post> getAllByUserId(String userId);
    Post getByIdAndUserId(Long postId, String userId);
}
