import baseApi from "../baseApi";

export interface Product {
    id: number;
    name: string;
    price: number;
    location: string;
    timeAgo: string;
    imageUrl: string;
}

export const getProducts = async () => {
    const response = await baseApi.get<Product[]>("/products");
    return response.data;
};

export const getProductById = async (id: number) => {
    const response = await baseApi.get<Product>(`/products/${id}`);
    return response.data;
};

export const createProduct = async (product: Omit<Product, "id">) => {
    const response = await baseApi.post("/products", product);
    return response.data;
};

export const deleteProduct = async (id: number) => {
    const response = await baseApi.delete(`/products/${id}`);
    return response.data;
};
