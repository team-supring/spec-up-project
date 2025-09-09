"use client";

import { useState, ChangeEvent } from "react";
import { useForm } from "react-hook-form";
import { motion, AnimatePresence, easeInOut } from "framer-motion";
import styles from "./signup.module.css";
import { IoIosArrowBack } from "react-icons/io";
import { useRouter } from "next/navigation";
import Toast, { showToast } from "../../components/toast";
import Footer from "../../components/loginFooter";
import { signup, checkLoginIdExists } from "@/apis/login/auth";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

interface SignUpForm {
    memberId: string;
    memberPassword: string;
    name: string;
    birth: string;
    sex: string;
    email: string;
}

const schema = z.object({
    memberId: z.string().min(1, "아이디를 입력해주세요"),
    memberPassword: z
        .string()
        .min(4, "비밀번호는 4자 이상이어야 합니다")
        .max(20, "비밀번호는 20자 이내로 입력해주세요"),
    name: z.string().min(1, "이름을 입력해주세요"),
    birth: z.string().length(6, "생년월일 6자리를 입력해주세요"),
    sex: z.string().refine((val) => ["1", "2", "3", "4"].includes(val), {
        message: "성별 코드는 1,2,3,4 중 하나여야 합니다",
    }),
    email: z.string().email("올바른 이메일 형식이 아닙니다"),
});

export default function SignUp() {
    const {
        register,
        handleSubmit,
        formState: { errors },
        clearErrors,
        watch,
        setValue,
    } = useForm<SignUpForm>({
        resolver: zodResolver(schema),
        defaultValues: {
            memberId: "",
            memberPassword: "",
            name: "",
            email: "",
            birth: "",
            sex: "",
        },
    });

    const [shakeKey, setShakeKey] = useState(0);
    const [isUserIdAvailable, setIsUserIdAvailable] = useState<boolean>(false);
    const router = useRouter();
    const shakeAnimation = {
        x: [0, -5, 5, -5, 5, 0],
        transition: { duration: 0.3, ease: easeInOut },
    };
    const [isCheckingId, setIsCheckingId] = useState<boolean>(false);

    const handleInputChange = (fieldName: keyof SignUpForm, value: string) => {
        if (/\s/.test(value)) {
            showToast("error", "공백은 허용되지 않습니다.");
            setValue(fieldName, value.trim());
            return;
        }
        clearErrors(fieldName);
    };

    const handleDuplicateCheck = async () => {
        const userId = watch("memberId");

        if (!userId) {
            showToast("error", "아이디를 입력해주세요.");
            return;
        }

        if (isCheckingId) return;
        console.log("Checking ID:", userId);
        if (userId) {
            try {
                const response = await checkLoginIdExists(userId);
                if (response) {
                    setIsUserIdAvailable(false);
                    showToast("error", "중복된 아이디입니다.");
                } else {
                    setIsUserIdAvailable(true);
                    showToast("success", "사용 가능한 아이디입니다.");
                }
            } catch (error) {
                console.error("중복 검사 실패:", error);
                showToast("error", "중복 검사 중 오류가 발생했습니다.");
            }
        }
    };

    const onSubmit = async (data: SignUpForm) => {
        if (isUserIdAvailable === false) {
            showToast("error", "아이디 중복 검사를 진행해주세요.");
            return;
        }

        try {
            await signup(
                data.memberId,
                data.memberPassword,
                data.name,
                data.email,
                data.birth,
                data.sex
            );
            router.push("/login");
        } catch (err) {
            console.error("회원가입 실패:", err);
            showToast("error", "회원가입 실패! 다시 시도해주세요.");
        }
    };

    return (
        <div className={styles.signUpWrapper}>
            <Toast />
            <AnimatePresence mode="wait">
                <motion.div
                    key="signupForm"
                    initial={{ opacity: 0, scale: 1 }}
                    animate={{ opacity: 1, scale: 1 }}
                    exit={{ opacity: 0, scale: 1 }}
                    transition={{ duration: 0.5, ease: "easeInOut" }}
                    className={styles.formContainer}
                >
                    <div className={styles.signupText}>
                        <IoIosArrowBack
                            className={styles.backIcon}
                            onClick={() => router.push("/login")}
                        />
                        <div>회원 가입</div>
                    </div>

                    <form onSubmit={handleSubmit(onSubmit)}>
                        <motion.div
                            key={`memberId-${shakeKey}`}
                            className={`${styles.idForm} ${
                                errors.memberId ? styles.inputError : ""
                            }`}
                            animate={errors.memberId ? shakeAnimation : {}}
                        >
                            <div className={styles.idInfoText}>아이디</div>
                            <div className={styles.idInputGroup}>
                                <input
                                    type="text"
                                    placeholder="아이디 4글자 이상"
                                    {...register("memberId", {
                                        required: true,
                                    })}
                                    className={styles.idInput}
                                    onChange={(e) =>
                                        handleInputChange(
                                            "memberId",
                                            e.target.value
                                        )
                                    }
                                />
                                <button
                                    type="button"
                                    className={styles.idCheckButton}
                                    onClick={handleDuplicateCheck}
                                    disabled={isCheckingId}
                                >
                                    {isCheckingId ? "검사 중..." : "중복 검사"}
                                </button>
                            </div>
                        </motion.div>

                        <motion.div
                            key={`memberPassword-${shakeKey}`}
                            className={`${styles.passwordForm} ${
                                errors.memberPassword ? styles.inputError : ""
                            }`}
                            animate={
                                errors.memberPassword ? shakeAnimation : {}
                            }
                        >
                            <div className={styles.pwInfoText}>비밀번호</div>
                            <input
                                type="password"
                                placeholder="비밀번호 입력"
                                {...register("memberPassword", {
                                    required: true,
                                })}
                                className={styles.pwInput}
                                onChange={(e) =>
                                    handleInputChange(
                                        "memberPassword",
                                        e.target.value
                                    )
                                }
                            />
                        </motion.div>

                        <motion.div
                            key={`name-${shakeKey}`}
                            className={`${styles.name} ${
                                errors.name ? styles.inputError : ""
                            }`}
                            animate={errors.name ? shakeAnimation : {}}
                        >
                            <div>
                                <div className={styles.nameInfoText}>이름</div>
                                <input
                                    type="text"
                                    placeholder="이름 입력"
                                    {...register("name", { required: true })}
                                    className={styles.nameInput}
                                    onChange={(e) =>
                                        handleInputChange(
                                            "name",
                                            e.target.value
                                        )
                                    }
                                />
                            </div>
                        </motion.div>

                        <motion.div
                            key={`birth-${shakeKey}`}
                            className={`${styles.birth} ${
                                errors.birth || errors.sex
                                    ? styles.inputError
                                    : ""
                            }`}
                            animate={
                                errors.birth || errors.sex ? shakeAnimation : {}
                            }
                        >
                            <div>
                                <div className={styles.birthInfoText}>
                                    주민등록 앞 7자리
                                </div>
                                <div className={styles.birthGender}>
                                    <input
                                        type="text"
                                        placeholder="생년월일"
                                        {...register("birth", {
                                            required:
                                                "생년월일 6자리를 입력해주세요.",
                                            pattern: {
                                                value: /^\d{6}$/,
                                                message:
                                                    "6자리 숫자를 입력해주세요.",
                                            },
                                        })}
                                        maxLength={6}
                                        className={styles.birthInput}
                                        onChange={(e) =>
                                            handleInputChange(
                                                "birth",
                                                e.target.value
                                            )
                                        }
                                    />
                                    <div className={styles.dod}>-</div>
                                    <div className={styles.genderGroup}>
                                        <input
                                            type="text"
                                            placeholder="0"
                                            {...register("sex", {
                                                required:
                                                    "성별 코드 1자리를 입력해주세요.",
                                                pattern: {
                                                    value: /^[1-4]$/,
                                                    message:
                                                        "1에서 4 사이의 숫자를 입력해주세요.",
                                                },
                                            })}
                                            maxLength={1}
                                            className={styles.genderInput}
                                            onChange={(e) =>
                                                handleInputChange(
                                                    "sex",
                                                    e.target.value
                                                )
                                            }
                                        />
                                        <div className={styles.maskedValue}>
                                            {Array.from({ length: 6 }).map(
                                                (_, i) => (
                                                    <span
                                                        key={i}
                                                        className={
                                                            styles.maskedDot
                                                        }
                                                    >
                                                        ●
                                                    </span>
                                                )
                                            )}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </motion.div>

                        <motion.div
                            key={`email-${shakeKey}`}
                            className={`${styles.authInputGroup} ${
                                errors.email ? styles.inputError : ""
                            }`}
                            animate={errors.email ? shakeAnimation : {}}
                        >
                            <div className={styles.emailInfoText}>이메일</div>
                            <input
                                type="email"
                                placeholder="SpecUp@div.com"
                                {...register("email", { required: true })}
                                className={styles.emailInput}
                                onChange={(e) =>
                                    handleInputChange("email", e.target.value)
                                }
                            />
                        </motion.div>

                        <div className={styles.buttonGroup}>
                            <button
                                type="submit"
                                className={styles.signupButton}
                            >
                                회원가입
                            </button>
                        </div>
                    </form>
                </motion.div>
            </AnimatePresence>
            <div className={styles.footerWrapper}>
                <Footer />
            </div>
        </div>
    );
}
