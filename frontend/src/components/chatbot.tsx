"use client";
import { useState } from "react";
import styles from "./chatbot.module.css";

interface ChatbotWidgetProps {
    isOpen: boolean;
    onClose: () => void;
}

const ChatbotWidget = ({ isOpen, onClose }: ChatbotWidgetProps) => {
    const [messages, setMessages] = useState<
        { role: "user" | "bot"; text: string }[]
    >([]);
    const [input, setInput] = useState("");

    const handleSend = () => {
        if (!input.trim()) return;

        setMessages([...messages, { role: "user", text: input }]);

        setInput("");
    };

    if (!isOpen) return null;

    return (
        <div className={styles.chatWindow}>
            <div className={styles.chatHeader}>
                <span>챗봇</span>
                <button onClick={onClose}>❌</button>
            </div>
            <div className={styles.chatMessages}>
                {messages.map((m, i) => (
                    <div
                        key={i}
                        className={
                            m.role === "user"
                                ? styles.userMessage
                                : styles.botMessage
                        }
                    >
                        {m.text}
                    </div>
                ))}
            </div>
            <div className={styles.chatInputArea}>
                <input
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    className={styles.chatInput}
                    placeholder="메시지를 입력하세요..."
                    onKeyDown={(e) => {
                        if (e.key === "Enter") handleSend();
                    }}
                />
                <button onClick={handleSend} className={styles.sendButton}>
                    전송
                </button>
            </div>
        </div>
    );
};

export default ChatbotWidget;
