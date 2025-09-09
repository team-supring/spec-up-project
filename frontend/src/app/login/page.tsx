"use client";

import { useForm, SubmitHandler } from "react-hook-form";
import { useRouter } from "next/navigation";
import styles from "./login.module.css";
import classNames from "classnames";
import Image from "next/image";
import { FC, FormEvent, useState } from "react";
import logo from "@/assets/logo.png";
import { motion, easeInOut } from "framer-motion";
import GoogleLoginButton from "@/components/googleLogin";
import KakaoLoginButton from "@/components/kakaoLogin";
import NaverLoginButton from "@/components/naverLogin";
import Footer from "@/components/loginFooter";
import { login } from "@/apis/login/auth";

interface LoginForm {
    memberId: string;
    memberPassword: string;
}

export default function SignIn() {
    const {
        register,
        handleSubmit,
        formState: { errors },
        trigger,
    } = useForm<LoginForm>({
        shouldFocusError: false,
    });

    const [memberId, setMemberId] = useState<string>("");
    const [memberPassword, setMemberPassword] = useState<string>("");
    const [error, setError] = useState<string>("");
    const router = useRouter();
    const [shakeKey, setShakeKey] = useState(0);

    const shakeAnimation = {
        x: [0, -5, 5, -5, 5, 0],
        transition: { duration: 0.3, ease: easeInOut },
    };

    const handleClick = async () => {
        const result = await trigger(["memberId", "memberPassword"]);
        if (!result) setShakeKey((prev) => prev + 1);
    };

    const onSubmitLogin: SubmitHandler<LoginForm> = async (data) => {
        setError("");

        try {
            await login(memberId, memberPassword);
            router.push("/main");
        } catch (err: any) {
            console.error("Login failed:", err);
            if (
                err.message === "로그인 실패: 아이디와 비밀번호를 확인해주세요."
            ) {
                alert(err.message);
            } else {
                setError(err.message);
            }
        }
    };

    return (
        <div className={styles.wrapper}>
            <motion.div
                key="signin-form"
                initial={{ opacity: 0, scale: 1 }}
                animate={{ opacity: 1, scale: 1 }}
                exit={{ opacity: 0, scale: 1 }}
                transition={{ duration: 0.5, ease: easeInOut }}
                className={styles.container}
            >
                <div className={styles.logoForm}>
                    <Image src={logo} className={styles.loginImg} alt="logo" />
                </div>

                <div className={styles.signUp}>
                    계정이 없으신가요?{" "}
                    <span
                        className={styles.signUpButton}
                        onClick={() => router.push("/signup")}
                    >
                        가입하기
                    </span>
                </div>

                <form onSubmit={handleSubmit(onSubmitLogin)}>
                    <motion.div
                        key={`memberId-${shakeKey}`}
                        animate={errors.memberId ? shakeAnimation : undefined}
                        className={classNames(styles.idForm, {
                            [styles.inputErrorContainer]: errors.memberId,
                        })}
                    >
                        <div className={styles.idText}>아이디</div>
                        <input
                            type="text"
                            {...register("memberId", { required: true })}
                            className={styles.memberId}
                            onChange={(e) => setMemberId(e.target.value)}
                        />
                    </motion.div>

                    <motion.div
                        key={`memberPassword-${shakeKey}`}
                        animate={
                            errors.memberPassword ? shakeAnimation : undefined
                        }
                        className={classNames(styles.passwordForm, {
                            [styles.inputErrorContainer]: errors.memberPassword,
                        })}
                    >
                        <div className={styles.passwordText}>비밀번호</div>
                        <input
                            type="password"
                            {...register("memberPassword", { required: true })}
                            className={styles.memberPassword}
                            onChange={(e) => setMemberPassword(e.target.value)}
                        />
                    </motion.div>

                    <div className={styles.button}>
                        <button
                            type="submit"
                            className={styles.loginButton}
                            onClick={handleClick}
                        >
                            로그인
                        </button>
                    </div>
                </form>
                <div className={styles.divider}>소셜로 로그인</div>

                <div className={styles.socialButtons}>
                    <KakaoLoginButton
                        onSuccess={(res) => console.log("카카오 토큰:", res)}
                        className={styles.kakaoButton}
                    />

                    <GoogleLoginButton
                        onSuccess={(res) => console.log("토큰 받아옴:", res)}
                        onError={() => alert("구글 로그인 실패")}
                        className={styles.googleButton}
                    />

                    <NaverLoginButton className={styles.naverButton} />
                </div>
            </motion.div>
            <div className={styles.footerWrapper}>
                <Footer />
            </div>
        </div>
    );
}
