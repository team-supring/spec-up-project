"use client";

import { useState } from "react";
import styles from "./inquiryboard.module.css";

interface Inquiry {
    id: number;
    title: string;
    content: string;
}

export default function InquiryBoard() {
    const [inquiries, setInquiries] = useState<Inquiry[]>([]);
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!title || !content) return;

        const newInquiry: Inquiry = {
            id: Date.now(),
            title,
            content,
        };
        setInquiries([newInquiry, ...inquiries]);
        setTitle("");
        setContent("");
    };

    return (
        <div className={styles.boardWrapper}>
            <form onSubmit={handleSubmit} className={styles.form}>
                <input
                    type="text"
                    placeholder="제목"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    className={styles.input}
                />
                <textarea
                    placeholder="문의 내용을 입력하세요"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    className={styles.textarea}
                />
                <button type="submit" className={styles.submitButton}>
                    등록하기
                </button>
            </form>

            <div className={styles.list}>
                {inquiries.length === 0 && (
                    <div className={styles.empty}>등록된 문의가 없습니다.</div>
                )}
                {inquiries.map((inq) => (
                    <div key={inq.id} className={styles.item}>
                        <h4>{inq.title}</h4>
                        <p>{inq.content}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}
