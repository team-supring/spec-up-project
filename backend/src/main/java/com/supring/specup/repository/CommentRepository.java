package com.supring.specup.repository;

import com.supring.specup.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글 목록을 최신순으로 조회 (작성자 정보 포함)
    @Query("""
                SELECT c
                FROM Comment c
                JOIN FETCH c.user u
                WHERE c.post.postId = :postId
                ORDER BY c.createdAt ASC
            """)
    List<Comment> findByPostIdWithUser(@Param("postId") Long postId);
}
