// app/board/[postId]/page.tsx
"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import {
    getPostDetail,
    Post,
    getCommentList,
    createComment,
    Comment,
} from "@/apis/board/board";
import Header from "@/components/header/header";
import styles from "./boardDetail.module.css";

const PostDetailPage = () => {
    const params = useParams();
    const router = useRouter();
    const postId = Number(params.postId);

    const [post, setPost] = useState<Post | null>(null);
    const [comments, setComments] = useState<Comment[]>([]);
    const [newComment, setNewComment] = useState("");

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const data = await getPostDetail(postId);
                setPost(data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchPost();
    }, [postId]);

    useEffect(() => {
        const fetchComments = async () => {
            try {
                const data = await getCommentList(postId);
                setComments(data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchComments();
    }, [postId]);

    const handleAddComment = async () => {
        if (!newComment.trim()) return;
        try {
            const saved = await createComment(postId, { content: newComment });
            setComments((prev) => [...prev, saved]);
            setNewComment("");
        } catch (error) {
            console.error(error);
        }
    };

    if (!post)
        return <p className={styles.container}>게시글을 불러오는 중...</p>;

    return (
        <div className={styles.container}>
            <Header />

            <h1 className={styles.title}>{post.title}</h1>
            <p className={styles.meta}>
                {post.authorName} · {new Date(post.createdAt).toLocaleString()}
            </p>
            <div
                className={styles.content}
                dangerouslySetInnerHTML={{ __html: post.content ?? "" }}
            />

            <section className={styles.commentsSection}>
                <h2>댓글 {comments.length}</h2>
                <ul className={styles.commentList}>
                    {comments.map((comment) => (
                        <li
                            key={comment.commentId}
                            className={styles.commentItem}
                        >
                            <p className={styles.commentAuthor}>
                                {comment.author}
                            </p>
                            <p className={styles.commentText}>
                                {comment.content}
                            </p>
                            <span className={styles.commentDate}>
                                {comment.createdAt}
                            </span>
                        </li>
                    ))}
                </ul>

                <div className={styles.commentForm}>
                    <textarea
                        placeholder="댓글을 입력하세요..."
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                    />
                    <button onClick={handleAddComment}>댓글 작성</button>
                </div>
            </section>

            <button className={styles.backButton} onClick={() => router.back()}>
                뒤로가기
            </button>
        </div>
    );
};

export default PostDetailPage;
