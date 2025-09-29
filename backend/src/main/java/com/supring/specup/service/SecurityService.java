package com.supring.specup.service;

import com.supring.specup.repository.CommunityPostRepository;
import com.supring.specup.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final CommunityPostRepository postRepository;
    private final CommentRepository commentRepository;

    public boolean isPostOwner(Long postId, String memberId) {
        return postRepository.findById(postId)
                .map(post -> post.getUser().getMemberId().equals(memberId))
                .orElse(false);
    }

    public boolean isCommentOwner(Long commentId, String memberId) {
        return commentRepository.findById(commentId)
                .map(comment -> comment.getUser().getMemberId().equals(memberId))
                .orElse(false);
    }
}
