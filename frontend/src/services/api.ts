import axios, { AxiosError } from 'axios';
import type { SignUpRequest, LoginRequest, TokenResponse, UserResponse } from '../types/auth.types';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request 인터셉터: 토큰 자동 추가
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response 인터셉터: 토큰 만료 시 자동 갱신
api.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as any;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = localStorage.getItem('refreshToken');
        if (refreshToken) {
          const response = await axios.post(`${API_BASE_URL}/auth/refresh`, {
            refreshToken,
          });

          const { accessToken, refreshToken: newRefreshToken } = response.data;
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('refreshToken', newRefreshToken);

          originalRequest.headers.Authorization = `Bearer ${accessToken}`;
          return api(originalRequest);
        }
      } catch (refreshError) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export const authAPI = {
  // 회원가입
  signUp: async (data: SignUpRequest): Promise<UserResponse> => {
    const response = await api.post<UserResponse>('/auth/signup', data);
    return response.data;
  },

  // 로그인
  login: async (data: LoginRequest): Promise<TokenResponse> => {
    const response = await api.post<TokenResponse>('/auth/login', data);
    return response.data;
  },

  // 로그인 ID 중복 확인
  checkLoginId: async (loginId: string): Promise<boolean> => {
    const response = await api.get<boolean>('/auth/check/login-id', {
      params: { loginId },
    });
    return response.data;
  },

  // 내 정보 조회
  getMyInfo: async (): Promise<UserResponse> => {
    const response = await api.get<UserResponse>('/user/me');
    return response.data;
  },
};

export default api;