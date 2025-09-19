"use client";
import { useRouter } from "next/navigation";
import styles from "./floatingmenu.module.css";
import ChatbotWidget from "./chatbot";
import { useState } from "react";

import { FaRobot, FaUser, FaRegClock, FaStar } from "react-icons/fa";

const FloatingMenu = () => {
  const router = useRouter();
  const [showChatbot, setShowChatbot] = useState(false);

  return (
    <>
      <div className={styles.floatingMenu}>
        <button className={styles.menuItem} onClick={() => router.push("/wishlist")}>
          <FaStar className={styles.icon} />
        </button>
        <button className={styles.menuItem} onClick={() => router.push("/mypage")}>
          <FaUser className={styles.icon} />
        </button>
        <button className={styles.menuItem} onClick={() => router.push("/recent")}>
          <FaRegClock className={styles.icon} />
        </button>
        <button className={styles.menuItem} onClick={() => setShowChatbot(true)}>
          <FaRobot className={styles.icon} />
        </button>
      </div>

      <ChatbotWidget isOpen={showChatbot} onClose={() => setShowChatbot(false)} />
    </>
  );
};

export default FloatingMenu;
