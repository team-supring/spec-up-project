package com.supring.specup.repository;

import com.supring.specup.domain.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    // 최신순으로 페이징 처리하여 게시글 목록 조회
    Page<CommunityPost> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 특정 게시글과 작성자 정보 함께 조회 (N+1 문제 해결)
    @Query("""
                SELECT p
                FROM CommunityPost p
                JOIN FETCH p.user u
                WHERE p.postId = :postId
            """)
    CommunityPost findByIdWithUser(@Param("postId") Long postId);
}
