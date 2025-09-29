"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

import styles from "./write.module.css";
import Header from "@/components/header/header";
import Toast, { showToast } from "@/components/toast";
import { createPost } from "@/apis/board/board";

const WritePage = () => {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [loading, setLoading] = useState(false);
    const router = useRouter();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (loading) return;

        setLoading(true);
        try {
            await createPost({ title, content });
            showToast("success", "게시글이 등록되었습니다!");
            setTimeout(() => router.push("/board"), 1000);
        } catch (error) {
            console.error(error);
            showToast("error", "게시글 등록에 실패했습니다.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={styles.container}>
            <Header />
            <h2 className={styles.title}>✏️ 글쓰기</h2>
            <form onSubmit={handleSubmit} className={styles.form}>
                <input
                    type="text"
                    placeholder="제목을 입력하세요"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                    className={styles.formInput}
                />

                {/* CKEditor */}
                <div className={styles.editorWrapper}>
                    <CKEditor
                        editor={ClassicEditor as any}
                        data={content}
                        onChange={(_, editor) => setContent(editor.getData())}
                        onReady={(editor: any) => {
                            const editable = editor.ui.view.editable.element;


                            const fixHeight = () => {
                                editable.style.height = "400px";
                                editable.style.overflowY = "auto";
                            };


                            fixHeight();

                            const observer = new MutationObserver(fixHeight);
                            observer.observe(editable, {
                                attributes: true,
                                childList: false,
                                subtree: false,
                            });


                            editor.on("destroy", () => observer.disconnect());
                        }}
                        config={{
                            toolbar: [
                                "heading",
                                "|",
                                "bold",
                                "italic",
                                "underline",
                                "strikethrough",
                                "link",
                                "bulletedList",
                                "numberedList",
                                "|",
                                "blockQuote",
                                "insertTable",
                                "undo",
                                "redo",
                            ],
                        }}
                    />
                </div>

                <div className={styles.actions}>
                    <button
                        type="submit"
                        disabled={loading}
                        className={styles.submitBtn}
                    >
                        {loading ? "등록 중..." : "등록"}
                    </button>
                    <button
                        type="button"
                        onClick={() => router.push("/board")}
                        disabled={loading}
                        className={styles.cancelBtn}
                    >
                        취소
                    </button>
                </div>
            </form>
            <Toast />
        </div>
    );
};

export default WritePage;
