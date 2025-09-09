"use client";

import KakaoLogin from "react-kakao-login";
import Image from "next/image";
import kakaoIcon from "../assets/kakao.png";

interface KakaoLoginButtonProps {
  onSuccess?: (res: any) => void;
  onFail?: (err: any) => void;
  onLogout?: () => void;
  className?: string;
}

export default function KakaoLoginButton({
  onSuccess,
  onFail,
  onLogout,
  className,
}: KakaoLoginButtonProps) {
  return (
    <KakaoLogin
      token={process.env.NEXT_PUBLIC_KAKAO_JAVASCRIPT_KEY || ""}
      onSuccess={(res) => {
        console.log("카카오 로그인 성공", res);
        onSuccess?.(res);
      }}
      onFail={(err) => {
        console.error("카카오 로그인 실패", err);
        onFail?.(err);
      }}
      onLogout={() => {
        console.log("카카오 로그아웃");
        onLogout?.();
      }}
      render={({ onClick }) => (
        <button
          onClick={onClick}
          className={className}
          style={{
            border: "none",
            background: "none",
            cursor: "pointer",
            padding: 0,
          }}
        >
          <Image src={kakaoIcon} alt="kakao login"/>
        </button>
      )}
    />
  );
}
