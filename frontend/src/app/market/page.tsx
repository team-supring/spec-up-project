"use client";

import type { NextPage } from "next";
import styles from "./market.module.css";
import product1 from "@/assets/product1.png";
import product2 from "@/assets/product2.png";
import product3 from "@/assets/product3.png";
import { useState } from "react";
import Header from "@/components/header/header";
import FloatingMenu from "@/components/floatingmenu";

interface Product {
    id: number;
    name: string;
    price: number;
    location: string;
    timeAgo: string;
    imageUrl: string;
}

const Market: NextPage = () => {
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

    return (
        <div className={styles.container}>
            <Header />

            <div className={styles.content}>
                <aside className={styles.filter}>
                    <h3>필터</h3>

                    <div className={styles.filterGroup}>
                        <label>
                            <input type="checkbox" /> 거래 가능만 보기
                        </label>
                    </div>

                    <div className={styles.filterGroup}>
                        <p className={styles.filterTitle}>카테고리</p>
                        <div className={styles.filterOptions}>
                            <label>
                                <input type="checkbox" /> 디지털기기
                            </label>
                            <label>
                                <input type="checkbox" /> 생활가전
                            </label>
                            <label>
                                <input type="checkbox" /> 가구/인테리어
                            </label>
                            <label>
                                <input type="checkbox" /> 생활/주방
                            </label>
                            <label>
                                <input type="checkbox" /> 유아동
                            </label>
                            <label>
                                <input type="checkbox" /> 여성의류
                            </label>
                            <label>
                                <input type="checkbox" /> 여성잡화
                            </label>
                            <label>
                                <input type="checkbox" /> 남성패션/잡화
                            </label>
                            <label>
                                <input type="checkbox" /> 뷰티/미용
                            </label>
                            <label>
                                <input type="checkbox" /> 스포츠/레저
                            </label>
                            <label>
                                <input type="checkbox" /> 취미/게임/음반
                            </label>
                            <label>
                                <input type="checkbox" /> 기타 중고물품
                            </label>
                            <label>
                                <input type="checkbox" /> 삽니다
                            </label>
                        </div>
                    </div>

                    <div className={styles.filterGroup}>
                        <p className={styles.filterTitle}>가격대</p>
                        <div className={styles.filterOptions}>
                            <label>
                                <input type="checkbox" /> 나눔
                            </label>
                            <label>
                                <input type="checkbox" /> 5,000원 이하
                            </label>
                            <label>
                                <input type="checkbox" /> 10,000원 이하
                            </label>
                            <label>
                                <input type="checkbox" /> 20,000원 이하
                            </label>
                        </div>
                    </div>
                </aside>

                <main className={styles.productList}>
                    {products.map((product) => (
                        <div key={product.id} className={styles.productItem}>
                            <img src={product.imageUrl} alt={product.name} />
                            <p className={styles.name}>{product.name}</p>
                            <p className={styles.price}>
                                {product.price.toLocaleString()}원
                            </p>
                            <p className={styles.meta}>
                                {product.location} · {product.timeAgo}
                            </p>
                        </div>
                    ))}
                </main>
            </div>

            <FloatingMenu />
        </div>
    );
};

export default Market;
