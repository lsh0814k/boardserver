package com.fem.boardserver.post.application;

import com.fem.boardserver.post.application.outputport.PostRepository;
import com.fem.boardserver.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {
    private final PostRepository postRepository;

    public List<Post> getMyPosts(String userId) {
        return postRepository.getAllByUserId(userId);
    }
}
