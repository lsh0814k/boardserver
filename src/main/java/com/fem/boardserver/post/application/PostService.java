package com.fem.boardserver.post.application;

import com.fem.boardserver.post.application.outputport.PostRepository;
import com.fem.boardserver.post.domain.Post;
import com.fem.boardserver.post.domain.dto.PostCreateDto;
import com.fem.boardserver.post.domain.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void create(PostCreateDto postCreateDto) {
        Post post = Post.create(postCreateDto);
        postRepository.save(post);
    }

    public void update(Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.getById(postId);
        post.update(postUpdateDto);
    }

    public void delete(String userId, Long postId) {
        Post post = postRepository.getByIdAndUserId(postId, userId);
        postRepository.delete(post);
    }
}
