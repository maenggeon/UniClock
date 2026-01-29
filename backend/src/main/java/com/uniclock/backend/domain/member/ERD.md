
- user
  - user_id (PK)
  - login_id
  - password_hash
  - name
  - email
  - is_deleted
  - created_at
  - updated_at

* * *

- personal_task
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

- team_project
  - team_project_id (PK)
  - subject
  - title
  - description
  - start_date
  - end_date
  - status_id (FK)
  - created_at
  - updated_at

* * *

- team_member
  - team_member_id (PK)
  - team_project_id (FK)
  - user_id (FK)
  - role_id (FK)
  - created_at
  - updated_at

같은 유저가 같은 프로젝트에 중복 들어가는 거 방지:
UNIQUE (team_project_id, user_id)

* * *

- code_group
  - group_code (PK)
  - group_name

* * *

- code_detail
  - code_id (PK)
  - group_code (FK)
  - code_name
  - sort_order
