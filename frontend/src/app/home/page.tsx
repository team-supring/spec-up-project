"use client";

import type { NextPage } from "next";
import styles from "./home.module.css";
import logo from "@/assets/logo.png";
import product1 from "@/assets/product1.png";
import product2 from "@/assets/product2.png";
import product3 from "@/assets/product3.png";
import { useState } from "react";
import { useRouter } from "next/navigation";
import FloatingMenu from "@/components/floatingmenu";
import Header from "@/components/header/header";

interface Product {
    id: number;
    name: string;
    price: number;
    location: string;
    timeAgo: string;
    imageUrl: string;
}

interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
    createdAt: string;
    views: number;
}

const Home: NextPage = () => {
    const router = useRouter();

    const [products] = useState<Product[]>([
        {
            id: 1,
            name: "티모볼 탁구 라켓 팝니다",
            price: 15000,
            location: "성수동1가",
            timeAgo: "2시간 전",
            imageUrl: product1.src,
        },
        {
            id: 2,
            name: "상품 2 이름",
            price: 25000,
            location: "성수동2가",
            timeAgo: "1시간 전",
            imageUrl: product2.src,
        },
        {
            id: 3,
            name: "상품 3 이름",
            price: 30000,
            location: "성수동3가",
            timeAgo: "30분 전",
            imageUrl: product3.src,
        },
    ]);

    const [posts] = useState<Post[]>([
        {
            id: 1,
            title: "중고 책 팝니다",
            content: "안녕하세요. 필요하신 분 가져가세요.",
            author: "홍길동",
            createdAt: "2025-09-15",
            views: 23,
        },
        {
            id: 2,
            title: "강아지 분양해요",
            content: "귀여운 강아지 분양합니다.",
            author: "김철수",
            createdAt: "2025-09-14",
            views: 45,
        },
    ]);

    return (
        <div className={styles.container}>
            <Header />
            <section>
                <h2>🎁 우리 동네 추천 상품</h2>
                <div className={styles.productList}>
                    {products.map((product) => (
                        <div key={product.id} className={styles.productItem}>
                            <img src={product.imageUrl} alt={product.name} />
                            <p>{product.name}</p>
                            <p>{product.price.toLocaleString()}원</p>
                            <p>
                                {product.location} · {product.timeAgo}
                            </p>
                        </div>
                    ))}
                </div>
            </section>

            <section>
                <h2>🔥 우리 동네 인기글</h2>
                {posts.map((post) => (
                    <article key={post.id} className={styles.post}>
                        <h3>{post.title}</h3>
                        <p>{post.content}</p>
                        <small>
                            {post.author} / {post.createdAt} / 조회수{" "}
                            {post.views}
                        </small>
                    </article>
                ))}
            </section>

            <FloatingMenu />
        </div>
    );
};

export default Home;
