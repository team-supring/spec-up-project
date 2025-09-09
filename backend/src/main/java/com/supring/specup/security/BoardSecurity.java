package com.supring.specup.security;

import com.supring.specup.domain.RegionPost;
import com.supring.specup.repository.RegionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardSecurity {

    private final RegionPostRepository postRepository;

    // 로그인한 사용자가 글의 작성자인지 검증
    public boolean isOwner(String username, Long postId) {
        return postRepository.findById(postId)
                .map(RegionPost::getAuthor)
                .map(author -> author.equals(username))
                .orElse(false);
    }
}
