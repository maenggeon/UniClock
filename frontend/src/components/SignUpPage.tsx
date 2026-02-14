// src/components/SignUpPage.tsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import './AuthPages.css';

interface FormData {
  loginId: string;
  password: string;
  passwordConfirm: string;
  name: string;
  email: string;
}

interface FormErrors {
  loginId?: string;
  password?: string;
  passwordConfirm?: string;
  name?: string;
  email?: string;
}

export const SignUpPage: React.FC = () => {
  const navigate = useNavigate();
  const { signUp, checkLoginId, loading, error } = useAuth();

  const [formData, setFormData] = useState<FormData>({
    loginId: '',
    password: '',
    passwordConfirm: '',
    name: '',
    email: '',
  });

  const [formErrors, setFormErrors] = useState<FormErrors>({});
  const [loginIdChecked, setLoginIdChecked] = useState(false);
  const [loginIdAvailable, setLoginIdAvailable] = useState(false);

  // loginId 변경 시 중복 확인 초기화
  useEffect(() => {
    setLoginIdChecked(false);
    setLoginIdAvailable(false);
  }, [formData.loginId]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    // 에러 메시지 제거
    setFormErrors((prev) => ({
      ...prev,
      [name]: undefined,
    }));
  };

  const handleCheckLoginId = async () => {
    if (!formData.loginId) {
      setFormErrors((prev) => ({ ...prev, loginId: '로그인 ID를 입력해주세요.' }));
      return;
    }

    if (formData.loginId.length < 4 || formData.loginId.length > 50) {
      setFormErrors((prev) => ({
        ...prev,
        loginId: '로그인 ID는 4자 이상 50자 이하여야 합니다.',
      }));
      return;
    }

    const exists = await checkLoginId(formData.loginId);
    setLoginIdChecked(true);
    setLoginIdAvailable(!exists);

    if (exists) {
      setFormErrors((prev) => ({ ...prev, loginId: '이미 사용 중인 로그인 ID입니다.' }));
    } else {
      setFormErrors((prev) => ({ ...prev, loginId: undefined }));
    }
  };

  const validateForm = (): boolean => {
    const errors: FormErrors = {};

    // 로그인 ID 검증
    if (!loginIdChecked || !loginIdAvailable) {
      errors.loginId = '로그인 ID 중복 확인이 필요합니다.';
    }

    // 비밀번호 검증
    if (formData.password.length < 8 || formData.password.length > 20) {
      errors.password = '비밀번호는 8자 이상 20자 이하여야 합니다.';
    } else if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/.test(formData.password)) {
      errors.password = '비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.';
    }

    // 비밀번호 확인
    if (formData.password !== formData.passwordConfirm) {
      errors.passwordConfirm = '비밀번호가 일치하지 않습니다.';
    }

    // 이름 검증
    if (formData.name.length < 2 || formData.name.length > 20) {
      errors.name = '이름은 2자 이상 20자 이하여야 합니다.';
    }

    // 이메일 검증
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      errors.email = '올바른 이메일 형식이 아닙니다.';
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    const { passwordConfirm, ...signUpData } = formData;
    const result = await signUp(signUpData);

    if (result) {
      alert('회원가입이 완료되었습니다.');
      navigate('/login');
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>회원가입</h2>
        <form onSubmit={handleSubmit}>
          {/* 로그인 ID */}
          <div className="form-group">
            <label htmlFor="loginId">로그인 ID</label>
            <div className="input-with-button">
              <input
                type="text"
                id="loginId"
                name="loginId"
                value={formData.loginId}
                onChange={handleChange}
                placeholder="4-50자의 영문, 숫자, _, - 사용 가능"
                className={formErrors.loginId ? 'error' : ''}
              />
              <button
                type="button"
                onClick={handleCheckLoginId}
                className="check-button"
                disabled={!formData.loginId}
              >
                중복확인
              </button>
            </div>
            {formErrors.loginId && <span className="error-message">{formErrors.loginId}</span>}
            {loginIdChecked && loginIdAvailable && (
              <span className="success-message">사용 가능한 ID입니다.</span>
            )}
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
              placeholder="8-20자의 영문, 숫자, 특수문자 포함"
              className={formErrors.password ? 'error' : ''}
            />
            {formErrors.password && <span className="error-message">{formErrors.password}</span>}
          </div>

          {/* 비밀번호 확인 */}
          <div className="form-group">
            <label htmlFor="passwordConfirm">비밀번호 확인</label>
            <input
              type="password"
              id="passwordConfirm"
              name="passwordConfirm"
              value={formData.passwordConfirm}
              onChange={handleChange}
              placeholder="비밀번호를 다시 입력해주세요"
              className={formErrors.passwordConfirm ? 'error' : ''}
            />
            {formErrors.passwordConfirm && (
              <span className="error-message">{formErrors.passwordConfirm}</span>
            )}
          </div>

          {/* 이름 */}
          <div className="form-group">
            <label htmlFor="name">이름</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="2-20자"
              className={formErrors.name ? 'error' : ''}
            />
            {formErrors.name && <span className="error-message">{formErrors.name}</span>}
          </div>

          {/* 이메일 */}
          <div className="form-group">
            <label htmlFor="email">이메일</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="example@email.com"
              className={formErrors.email ? 'error' : ''}
            />
            {formErrors.email && <span className="error-message">{formErrors.email}</span>}
          </div>

          {/* 서버 에러 메시지 */}
          {error && <div className="server-error">{error}</div>}

          {/* 제출 버튼 */}
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? '처리 중...' : '회원가입'}
          </button>
        </form>

        <div className="auth-footer">
          이미 계정이 있으신가요?{' '}
          <button onClick={() => navigate('/login')} className="link-button">
            로그인
          </button>
        </div>
      </div>
    </div>
  );
};