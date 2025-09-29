"use client";

import { useRouter } from "next/navigation";
import styles from "./header.module.css";
import logo from "@/assets/logo.png";

export default function Header() {
    const router = useRouter();

    return (
        <div>
            <div className={styles.loginHeader}>
                <button
                    className={styles.loginButton}
                    onClick={() => router.push("/login")}
                >
                    로그인
                </button>
                <span className={styles.divider}> / </span>
                <button
                    className={styles.signupButton}
                    onClick={() => router.push("/signup")}
                >
                    회원가입
                </button>
            </div>

            <header className={styles.header}>
                <div className={styles.logo}>
                    <img src={logo.src} className={styles.loginImg} onClick={() => router.push("/home")} alt="logo" />
                </div>
                <input
                    type="search"
                    placeholder="검색어를 입력해주세요"
                    className={styles.searchInput}
                    aria-label="검색어 입력"
                />
                <div>
                    <button className={styles.rankButton}>돈암 1등</button>
                </div>
            </header>

            <nav className={styles.nav}>
                <div className={styles.navItem}>내 동네 찾기</div>
                <div className={styles.navItem}>중고 거래</div>
                <div className={styles.navItem} onClick={() => router.push("/board")}>커뮤니티 게시판</div>
                <div className={styles.navItem}>채팅방</div>
                <div
                    className={styles.navItem}
                    onClick={() => router.push("/support")}
                    role="button"
                    tabIndex={0}
                >
                    고객센터
                </div>
            </nav>
        </div>
    );
}