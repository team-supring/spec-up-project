"use client";

import styles from "./support.module.css";
import FAQ from "./components/FAQ";
import InquiryBoard from "./components/InquiryBoard";
import Header from "@/components/header/header";
import FloatingMenu from "@/components/floatingmenu";

export default function Customer() {
    return (
        <div className={styles.wrapper}>
            <Header />
            <section className={styles.section}>
                <h2 className={styles.title}>자주 묻는 질문(FAQ)</h2>
                <FAQ />
            </section>

            <section className={styles.section}>
                <h2 className={styles.title}>1:1 문의하기</h2>
                <InquiryBoard />
            </section>
            <FloatingMenu />
        </div>
    );
}
