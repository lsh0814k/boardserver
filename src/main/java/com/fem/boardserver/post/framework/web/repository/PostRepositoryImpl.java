package com.fem.boardserver.post.framework.web.repository;

import com.fem.boardserver.post.application.outputport.PostRepository;
import com.fem.boardserver.post.domain.Post;
import com.fem.boardserver.user.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public Post getById(Long postId) {
        return postJpaRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 게시글 입니다."));
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.delete(post);
    }

    @Override
    public List<Post> getAllByUserId(String userId) {
        return postJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Post getByIdAndUserId(Long postId, String userId) {
        return postJpaRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 게시글 입니다."));
    }
}
