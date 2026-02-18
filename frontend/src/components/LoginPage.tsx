// src/components/LoginPage.tsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import './AuthPages.css';

interface LoginFormData {
  loginId: string;
  password: string;
}

export const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const { login, loading, error } = useAuth();

  const [formData, setFormData] = useState<LoginFormData>({
    loginId: '',
    password: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!formData.loginId || !formData.password) {
      alert('아이디와 비밀번호를 입력해주세요.');
      return;
    }

    const success = await login(formData);

    if (success) {
      navigate('/dashboard');
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>로그인</h2>
        <form onSubmit={handleSubmit}>
          {/* 로그인 ID */}
          <div className="form-group">
            <label htmlFor="loginId">로그인 ID</label>
            <input
              type="text"
              id="loginId"
              name="loginId"
              value={formData.loginId}
              onChange={handleChange}
              placeholder="로그인 ID를 입력하세요"
              autoComplete="username"
            />
          </div>

          {/* 비밀번호 */}
          <div className="form-group">
            <label htmlFor="password">비밀번호</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="비밀번호를 입력하세요"
              autoComplete="current-password"
            />
          </div>

          {/* 서버 에러 메시지 */}
          {error && <div className="server-error">{error}</div>}

          {/* 제출 버튼 */}
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? '로그인 중...' : '로그인'}
          </button>
        </form>

        <div className="auth-footer">
          계정이 없으신가요?{' '}
          <button onClick={() => navigate('/signup')} className="link-button">
            회원가입
          </button>
        </div>
      </div>
    </div>
  );
};