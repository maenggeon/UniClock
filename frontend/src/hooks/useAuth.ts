import { useState } from 'react';
import { authAPI } from '../services/api';
import type { SignUpRequest, LoginRequest, UserResponse } from '../types/auth.types';

export const useAuth = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const signUp = async (data: SignUpRequest): Promise<UserResponse | null> => {
    setLoading(true);
    setError(null);
    try {
      const response = await authAPI.signUp(data);
      return response;
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || '회원가입에 실패했습니다.';
      setError(errorMessage);
      return null;
    } finally {
      setLoading(false);
    }
  };

  const login = async (data: LoginRequest): Promise<boolean> => {
    setLoading(true);
    setError(null);
    try {
      const response = await authAPI.login(data);
      localStorage.setItem('accessToken', response.accessToken);
      localStorage.setItem('refreshToken', response.refreshToken);
      return true;
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || '로그인에 실패했습니다.';
      setError(errorMessage);
      return false;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  };

  const checkLoginId = async (loginId: string): Promise<boolean> => {
    try {
      return await authAPI.checkLoginId(loginId);
    } catch (err) {
      return false;
    }
  };

  return { signUp, login, logout, checkLoginId, loading, error };
};