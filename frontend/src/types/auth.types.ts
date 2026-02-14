// src/types/auth.types.ts

export interface SignUpRequest {
  loginId: string;
  password: string;
  name: string;
  email: string;
}

export interface LoginRequest {
  loginId: string;
  password: string;
}

export interface TokenResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
}

export interface UserResponse {
  userId: number;
  loginId: string;
  name: string;
  email: string;
  createdAt: string;
  updatedAt: string;
}

export interface ErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  errors?: Record<string, string>;
}