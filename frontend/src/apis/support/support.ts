import baseApi from "../baseApi";

export interface Faq {
    faqId: number;
    question: string;
    answer: string;
}

export const getFaqList = async (page: number = 1) => {
    const response = await baseApi.get<Faq[]>(`/faq?page=${page}`);
    return response.data;
};

export const createFaq = async (faq: Omit<Faq, "faqId">) => {
    const response = await baseApi.post("/faq", faq);
    return response.data;
};

export const updateFaq = async (faqId: number, faq: Omit<Faq, "faqId">) => {
    const response = await baseApi.put(`/faq/${faqId}`, faq);
    return response.data;
};

export const deleteFaq = async (faqId: number) => {
    const response = await baseApi.delete(`/faq/${faqId}`);
    return response.data;
};

export interface Cs {
    csId: number;
    csTitle: string;
    csDescription?: string;
    time: string;
    photo?: string[];
    csAnswer?: string;
    csAnswerYN: "Y" | "N";
}

export const getSupportLanding = async (page: number = 1) => {
    const response = await baseApi.get(`/support/landing/page=${page}`);
    return response.data;
};

export const getCsList = async (page: number = 1) => {
    const response = await baseApi.get<Cs[]>(`/cs?page=${page}`);
    return response.data;
};

export const getCsDetail = async (csId: number) => {
    const response = await baseApi.get<Cs>(`/cs/inquiry/${csId}`);
    return response.data;
};

export const createCs = async (cs: Omit<Cs, "csId" | "csAnswerYN">) => {
    const response = await baseApi.post("/cs/inquiry", {
        ...cs,
        csAnswerYN: "N",
    });
    return response.data;
};

export const updateCs = async (csId: number, cs: Partial<Cs>) => {
    const response = await baseApi.put(`/cs/inquiry/${csId}`, cs);
    return response.data;
};

export const deleteCs = async (csId: number) => {
    const response = await baseApi.delete(`/cs/inquiry/${csId}`);
    return response.data;
};

export const createAdminAnswer = async (csId: number, csAnswer: string) => {
    const response = await baseApi.post("/admin/cs/inquiry", {
        csId,
        csAnswer,
        csAnswerYN: "Y",
    });
    return response.data;
};

export const updateAdminAnswer = async (csId: number, csAnswer: string) => {
    const response = await baseApi.put(`/admin/cs/inquiry/${csId}`, {
        csId,
        csAnswer,
    });
    return response.data;
};

export const deleteAdminAnswer = async (csId: number) => {
    const response = await baseApi.delete(`/admin/cs/inquiry/${csId}`);
    return response.data;
};