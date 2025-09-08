"use client";

import { useForm, SubmitHandler } from "react-hook-form";
import { useRouter } from "next/navigation";
import styles from "./login.module.css";
import classNames from "classnames";
import { motion } from "framer-motion";
import { useState } from "react";

interface LoginForm {
    loginId: string;
    loginPassword: string;
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
    const router = useRouter();
    const [shakeKey, setShakeKey] = useState(0);

    const shakeAnimation = {
        x: [0, -5, 5, -5, 5, 0],
        transition: { duration: 0.3, ease: "easeInOut" },
    };

    const handleClick = async () => {
        const result = await trigger(["loginId", "loginPassword"]);
        if (!result) setShakeKey((prev) => prev + 1);
    };

    const onSubmitLogin: SubmitHandler<LoginForm> = async (data) => {
        try {
            const trimmedData = {
                loginId: data.loginId.trim(),
                loginPassword: data.loginPassword.trim(),
            };

            const res = await fetch("/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(trimmedData),
            });
            const result = await res.json();

            if (!result.error) {
                router.push("/home");
            } else {
                alert(result.message || "로그인 실패");
            }
        } catch (error: any) {
            alert(error.message);
        }
    };

    return (
        <div className={styles.wrapper}>
            <motion.div
                key="signin-form"
                initial={{ opacity: 0, scale: 1 }}
                animate={{ opacity: 1, scale: 1 }}
                exit={{ opacity: 0, scale: 1 }}
                transition={{ duration: 0.5, ease: "easeInOut" }}
                className={styles.container}
            >
                <div className={styles.logoForm}>
                    <img src="/icon/header.icon.png" className={styles.loginImg} alt="logo" />
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
                        key={`loginId-${shakeKey}`}
                        animate={errors.loginId ? shakeAnimation : {}}
                        className={classNames(styles.idForm, {
                            [styles.inputErrorContainer]: errors.loginId,
                        })}
                    >
                        <div className={styles.idText}>아이디</div>
                        <input
                            type="text"
                            {...register("loginId", { required: true })}
                            className={styles.loginId}
                        />
                    </motion.div>

                    <motion.div
                        key={`loginPassword-${shakeKey}`}
                        animate={errors.loginPassword ? shakeAnimation : {}}
                        className={classNames(styles.passwordForm, {
                            [styles.inputErrorContainer]: errors.loginPassword,
                        })}
                    >
                        <div className={styles.passwordText}>비밀번호</div>
                        <input
                            type="password"
                            {...register("loginPassword", { required: true })}
                            className={styles.loginPassword}
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
            </motion.div>
        </div>
    );
}
