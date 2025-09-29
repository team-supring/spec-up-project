"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import styles from "./board.module.css";
import Header from "@/components/header/header";
import { Post, getPostList } from "@/apis/board/board";

type SortOption = "latest" | "likes" | "comments";

const Board = () => {
    const [posts, setPosts] = useState<Post[]>([]);
    const [loading, setLoading] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");
    const [sort, setSort] = useState<SortOption>("latest");
    const router = useRouter();

    const fetchPosts = async () => {
        setLoading(true);
        try {
            const data = await getPostList(0, 10);
            setPosts(data);
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPosts();
    }, []);

    const filteredPosts = posts
        .filter((post) => post.title.includes(searchQuery))
        .sort((a, b) => {
            if (sort === "latest") {
                return (
                    new Date(b.createdAt).getTime() -
                    new Date(a.createdAt).getTime()
                );
            } else if (sort === "likes") {
                return (b.likes ?? 0) - (a.likes ?? 0);
            } else if (sort === "comments") {
                return (b.comments?.length ?? 0) - (a.comments?.length ?? 0);
            }
            return 0;
        });

    return (
        <div className={styles.container}>
            <Header />

            <div className={styles.searchWrapper}>
                <input
                    type="text"
                    placeholder="글을 검색해보세요"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    className={styles.searchInput}
                />
                <button className={styles.searchButton}>검색</button>
            </div>

            <div className={styles.topBar}>
                <div className={styles.sortTabs}>
                    {[
                        { label: "최신순", value: "latest" },
                        { label: "좋아요순", value: "likes" },
                        { label: "댓글 많은순", value: "comments" },
                    ].map((tab) => (
                        <div
                            key={tab.value}
                            className={`${styles.sortTab} ${
                                sort === tab.value ? styles.activeTab : ""
                            }`}
                            onClick={() => setSort(tab.value as SortOption)}
                        >
                            {tab.label}
                        </div>
                    ))}
                </div>

                <button
                    className={styles.writeButton}
                    onClick={() => router.push("/board/write")}
                >
                    글쓰기
                </button>
            </div>

            {loading ? (
                <p>게시글을 불러오는 중...</p>
            ) : (
                <div className={styles.postList}>
                    {filteredPosts.map((post) => (
                        <div
                            key={post.postId}
                            className={styles.postCard}
                            onClick={() => router.push(`/board/${post.postId}`)}
                            style={{ cursor: "pointer" }}
                        >
                            <h3 className={styles.postTitle}>{post.title}</h3>
                            <p className={styles.postMeta}>
                                {post.authorName} · {new Date(post.createdAt).toLocaleString()}
                            </p>
                            <div className={styles.postStats}>
                                <span>좋아요 {post.likes}</span>
                                <span>댓글 {post.comments?.length ?? 0}</span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Board;
