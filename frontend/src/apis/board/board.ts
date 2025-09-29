import baseApi from "../baseApi";

export interface Post {
    postId: number;
    title: string;
    authorName: string;
    createdAt: string;
    likes: number;
    content?: string | null;
    comments?: any[] | null;
}

export interface Comment {
    commentId: number;
    content: string;
    author: string;
    createdAt: string;
}

export const getPostList = async (page: number = 0, size: number = 5) => {
    const response = await baseApi.get(`/community?page=${page}&size=${size}`);
    return response.data.content;
};

export const getPostDetail = async (postId: number) => {
    const response = await baseApi.get<Post>(`/community/${postId}`);
    return response.data;
};

export type CreatePostInput = {
    title: string;
    content: string;
};

export const createPost = async (post: CreatePostInput) => {
    const response = await baseApi.post("/community", post);
    return response.data;
};

export const getCommentList = async (postId: number) => {
    const response = await baseApi.get<Comment[]>(
        `/community/${postId}/comments`
    );
    return response.data;
};

export const createComment = async (
    postId: number,
    comment: Omit<Comment, "commentId" | "author" | "createdAt">
) => {
    const response = await baseApi.post(
        `/community/${postId}/comments`,
        comment
    );
    return response.data;
};
