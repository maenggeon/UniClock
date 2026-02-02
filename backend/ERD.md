
- USERS
  - user_id (PK)
  - login_id
  - password_hash
  - password_updated_at
  - name
  - email
  - created_at
  - updated_at
  - deleted_at

* * *

- PERSONAL_TASK
  - task_id (PK)
  - user_id (FK)
  - subject
  - start_date
  - end_date
  - status_id (FK)
  - description
  - created_at
  - updated_at

* * *

- TEAM_PROJECT
  - team_project_id (PK)
  - owner_id (FK)
  - subject
  - title
  - description
  - start_date
  - end_date
  - status_id (FK)
  - created_at
  - updated_at

* * *

- TEAM_MEMBER
  - team_member_id (PK)
  - team_project_id (FK)
  - user_id (FK)
  - role_id (FK)
  - created_at
  - updated_at

같은 유저가 같은 프로젝트에 중복 들어가는 거 방지:
UNIQUE (team_project_id, user_id)

* * *

- CODE_GROUP
  - group_code (PK)
  - group_name

* * *

- CODE_DETAIL
  - code_id (PK)
  - group_code (FK)
  - code_name
  - sort_order
  - is_used
