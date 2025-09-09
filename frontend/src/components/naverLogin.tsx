"use client";

import Image from "next/image";
import naverIcon from "../assets/naver.png";

interface NaverLoginButtonProps {
  className?: string;
}

export default function NaverLoginButton({ className }: NaverLoginButtonProps) {
  const handleLogin = () => {
    const clientId = process.env.NEXT_PUBLIC_NAVER_CLIENT_ID;
    const callbackUrl = process.env.NEXT_PUBLIC_NAVER_CALLBACK_URL;
    const state = Date.now().toString();

    const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${clientId}&redirect_uri=${encodeURIComponent(
      callbackUrl!
    )}&state=${state}`;

    window.location.href = naverAuthUrl;
  };

  return (
    <button
      onClick={handleLogin}
      className={className}
      style={{
        border: "none",
        background: "none",
        cursor: "pointer",
        padding: 0,
      }}
    >
      <Image src={naverIcon} alt="naver login"/>
    </button>
  );
}
