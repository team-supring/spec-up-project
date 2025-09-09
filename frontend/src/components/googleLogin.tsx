"use client";

import { useGoogleLogin, TokenResponse } from "@react-oauth/google";
import Image from "next/image";
import googleIcon from "../assets/google.png";

interface GoogleLoginButtonProps {
  onSuccess?: (res: TokenResponse) => void;
  onError?: () => void;
  className?: string;
}

export default function GoogleLoginButton({
  onSuccess,
  onError,
  className,
}: GoogleLoginButtonProps) {
  const login = useGoogleLogin({
    onSuccess: (tokenResponse: TokenResponse) => {
      console.log("구글 로그인 성공", tokenResponse);
      onSuccess?.(tokenResponse);
    },
    onError: () => {
      console.error("구글 로그인 실패");
      onError?.();
    },
  });

  return (
    <button
      onClick={() => login()}
      className={className}
      style={{
        border: "none",
        background: "none",
        cursor: "pointer",
        padding: 0,
      }}
    >
      <Image src={googleIcon} alt="google login"/>
    </button>
  );
}
