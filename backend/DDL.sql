CREATE DATABASE uni_clock
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE uni_clock;

CREATE TABLE CODE_GROUP (
    group_code VARCHAR(20) PRIMARY KEY,
    group_name VARCHAR(50) NOT NULL
);

CREATE TABLE CODE_DETAIL (
    code_id VARCHAR(20) PRIMARY KEY,
    group_code VARCHAR(20) NOT NULL,
    code_name VARCHAR(50) NOT NULL,
    sort_order INT DEFAULT 0,
    is_used BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_code_group FOREIGN KEY (group_code) REFERENCES code_group(group_code)
);

CREATE TABLE USERS (
    user_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL UNIQUE ,
    password_hash VARCHAR(255) NOT NULL,
    password_updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT  CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

CREATE TABLE PERSONAL_TASK (
    task_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    subject VARCHAR(50) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status_id VARCHAR(20),
    description VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_task_status FOREIGN KEY (status_id) REFERENCES code_detail(code_id),

    CHECK (start_date <= end_date)
);

CREATE TABLE TEAM_PROJECT (
    team_project_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    owner_id INT UNSIGNED NOT NULL,
    subject VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(1000),
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status_id VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(user_id),
    CONSTRAINT fk_project_status FOREIGN KEY (status_id) REFERENCES code_detail(code_id),

    CHECK (start_date <= end_date)
);

CREATE TABLE TEAM_MEMBER (
    team_member_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    team_project_id BIGINT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    role_id VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 동일 유저 중복 참여 방지 제약
    UNIQUE KEY unique_project_user (team_project_id, user_id),
    CONSTRAINT fk_member_project FOREIGN KEY (team_project_id) REFERENCES team_project(team_project_id),
    CONSTRAINT fk_member_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_member_role FOREIGN KEY (role_id) REFERENCES code_detail(code_id)
);
