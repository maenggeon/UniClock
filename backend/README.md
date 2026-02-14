# Spring Boot Backend

## 스택
- Spring Boot 4.0.2
- Spring Security
- Spring Data JPA
- MySQL

## API
### 인증
- POST /api/auth/signup - 회원가입
- POST /api/auth/login - 로그인
- POST /api/auth/refrest - 토큰 재발급
### 사용자
- GET /api/users/me

## 구조
```
src/main/java/com/uniclock/backend/
└── common/
  ├── config/        # 설정 클래스
  ├── esception/     # 예외 클래스
  └── security/      # 보안 클래스
└── domain/
  ├── base/          # 공통 클래스
  ├── user/          # 사용자 클래스
  ├── task/          # 개인 과제 클래스
  └── project/       # 팀 프로젝트 클래스
```

## 개발 환경
- Java 21
- gradle
- MySQL