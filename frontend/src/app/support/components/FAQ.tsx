"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { FiChevronDown } from "react-icons/fi"; // react-icons에서 가져옴
import styles from "./faq.module.css";

interface FAQItem {
    question: string;
    answer: string;
}

const faqData: FAQItem[] = [
    {
        question: "비밀번호를 잊어버렸어요.",
        answer: "로그인 화면에서 '비밀번호 찾기'를 클릭하여 재설정할 수 있습니다.",
    },
    {
        question: "회원 탈퇴는 어떻게 하나요?",
        answer: "마이페이지 > 설정 > 회원 탈퇴 메뉴에서 진행할 수 있습니다.",
    },
    {
        question: "고객센터 운영시간이 어떻게 되나요?",
        answer: "평일 오전 9시 ~ 오후 6시까지 운영됩니다.",
    },
];

export default function FAQ() {
    const [openIndex, setOpenIndex] = useState<number | null>(null);

    const toggle = (index: number) => {
        setOpenIndex(openIndex === index ? null : index);
    };

    return (
        <div className={styles.faqContainer}>
            {faqData.map((item, index) => (
                <div key={index} className={styles.faqItem}>
                    <button
                        className={styles.question}
                        onClick={() => toggle(index)}
                    >
                        <span>{item.question}</span>
                        <motion.div
                            animate={{ rotate: openIndex === index ? 180 : 0 }}
                            transition={{ duration: 0.3 }}
                        >
                            <FiChevronDown size={20} />
                        </motion.div>
                    </button>

                    <AnimatePresence initial={false}>
                        {openIndex === index && (
                            <motion.div
                                className={styles.answer}
                                initial={{ height: 0, opacity: 0 }}
                                animate={{ height: "auto", opacity: 1 }}
                                exit={{ height: 0, opacity: 0 }}
                                transition={{ duration: 0.3, ease: "easeInOut" }}
                            >
                                <div className={styles.answerContent}>
                                    {item.answer}
                                </div>
                            </motion.div>
                        )}
                    </AnimatePresence>
                </div>
            ))}
        </div>
    );
}