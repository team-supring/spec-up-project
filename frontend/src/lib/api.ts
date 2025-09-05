/**
 * 백엔드 API 통신을 위한 유틸리티 함수들
 */

const API_BASE_URL = process.env.NODE_ENV === 'production' 
  ? '/api' 
  : 'http://localhost:8080/api';

/**
 * API 응답 타입 정의
 */
export interface ApiResponse<T = any> {
  success: boolean;
  data?: T;
  message?: string;
  error?: string;
}

/**
 * HTTP 메서드 타입
 */
type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH';

/**
 * API 요청 옵션
 */
interface ApiRequestOptions {
  method?: HttpMethod;
  headers?: Record<string, string>;
  body?: any;
  signal?: AbortSignal;
}

/**
 * 기본 API 요청 함수
 */
async function apiRequest<T>(
  endpoint: string, 
  options: ApiRequestOptions = {}
): Promise<ApiResponse<T>> {
  const {
    method = 'GET',
    headers = {},
    body,
    signal
  } = options;

  const url = `${API_BASE_URL}${endpoint}`;
  
  const requestOptions: RequestInit = {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers,
    },
    signal,
  };

  if (body && method !== 'GET') {
    requestOptions.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(url, requestOptions);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    return { success: true, data };
  } catch (error) {
    console.error('API request failed:', error);
    return {
      success: false,
      error: error instanceof Error ? error.message : 'Unknown error occurred'
    };
  }
}

/**
 * GET 요청
 */
export async function apiGet<T>(endpoint: string): Promise<ApiResponse<T>> {
  return apiRequest<T>(endpoint, { method: 'GET' });
}

/**
 * POST 요청
 */
export async function apiPost<T>(
  endpoint: string, 
  data: any
): Promise<ApiResponse<T>> {
  return apiRequest<T>(endpoint, { method: 'POST', body: data });
}

/**
 * PUT 요청
 */
export async function apiPut<T>(
  endpoint: string, 
  data: any
): Promise<ApiResponse<T>> {
  return apiRequest<T>(endpoint, { method: 'PUT', body: data });
}

/**
 * DELETE 요청
 */
export async function apiDelete<T>(endpoint: string): Promise<ApiResponse<T>> {
  return apiRequest<T>(endpoint, { method: 'DELETE' });
}

/**
 * PATCH 요청
 */
export async function apiPatch<T>(
  endpoint: string, 
  data: any
): Promise<ApiResponse<T>> {
  return apiRequest<T>(endpoint, { method: 'PATCH', body: data });
}

/**
 * 백엔드 상태 확인
 */
export async function checkBackendStatus(): Promise<boolean> {
  try {
    const response = await fetch('http://localhost:8080/status');
    return response.ok;
  } catch {
    return false;
  }
}

/**
 * 백엔드 API 테스트
 */
export async function testBackendApi(): Promise<ApiResponse<string>> {
  return apiGet<string>('/test');
}
