package com.fem.boardserver.post.framework.web;

import com.fem.boardserver.common.annotation.LoginUserId;
import com.fem.boardserver.common.dto.CommonResponse;
import com.fem.boardserver.post.application.PostReadService;
import com.fem.boardserver.post.application.PostService;
import com.fem.boardserver.post.application.dto.PostResponse;
import com.fem.boardserver.post.domain.Post;
import com.fem.boardserver.post.framework.web.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostReadService postReadService;

    @PostMapping
    public ResponseEntity<String> create(@LoginUserId String userId, @RequestBody PostRequest postRequest) {
        postService.create(postRequest.toCreateModel(userId));
        return ResponseEntity.status(CREATED)
                .build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> update(@LoginUserId String userId, @PathVariable Long postId, @RequestBody PostRequest postRequest) {
        postService.update(postId, postRequest.toUpdateModel(userId));
        return ResponseEntity.status(OK)
                .build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> delete(@LoginUserId String userId, @PathVariable Long postId) {
        postService.delete(userId, postId);
        return ResponseEntity.status(OK)
                .build();
    }

    @GetMapping("/my-list")
    public ResponseEntity<CommonResponse<List<PostResponse>>> getMyPosts(@LoginUserId String userId) {
        List<Post> myPosts = postReadService.getMyPosts(userId);
        CommonResponse<List<PostResponse>> commonResponse = CommonResponse.<List<PostResponse>>builder()
                .status(OK)
                .message("")
                .result(
                        myPosts.stream()
                                .map(PostResponse::from)
                                .toList()
                )
                .build();
        return ResponseEntity.ok(commonResponse);
    }
}
