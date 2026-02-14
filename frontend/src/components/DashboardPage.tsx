// src/components/DashboardPage.tsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authAPI } from '../services/api';
import type { UserResponse } from '../types/auth.types';
import { useAuth } from '../hooks/useAuth';
import './AuthPages.css';

export const DashboardPage: React.FC = () => {
  const navigate = useNavigate();
  const { logout } = useAuth();
  const [user, setUser] = useState<UserResponse | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const userInfo = await authAPI.getMyInfo();
        setUser(userInfo);
      } catch (error) {
        console.error('사용자 정보를 가져오는데 실패했습니다.', error);
        navigate('/login');
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, [navigate]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (loading) {
    return (
      <div className="auth-container">
        <div className="auth-card">로딩 중...</div>
      </div>
    );
  }

  return (
    <div className="auth-container">
      <div className="auth-card dashboard">
        <h2>대시보드</h2>
        {user && (
          <div className="user-info">
            <div className="info-row">
              <span className="label">사용자 ID:</span>
              <span className="value">{user.userId}</span>
            </div>
            <div className="info-row">
              <span className="label">로그인 ID:</span>
              <span className="value">{user.loginId}</span>
            </div>
            <div className="info-row">
              <span className="label">이름:</span>
              <span className="value">{user.name}</span>
            </div>
            <div className="info-row">
              <span className="label">이메일:</span>
              <span className="value">{user.email}</span>
            </div>
            <div className="info-row">
              <span className="label">가입일:</span>
              <span className="value">{new Date(user.createdAt).toLocaleString('ko-KR')}</span>
            </div>
          </div>
        )}
        <button onClick={handleLogout} className="submit-button logout-button">
          로그아웃
        </button>
      </div>
    </div>
  );
};