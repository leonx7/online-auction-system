INSERT INTO user_account (username, password, first_name, last_name, role, status, created_at) VALUES ( 'Ivan','$2a$10$/w0FnOH8P8Bkfd6yMqFI8uWIut.Gn4InVdkLec0SFwfYw2NIA9mlS',
'Ivan', 'Ivanov', 'admin', 'true', '20.02.2020');
INSERT INTO user_account (username, password, first_name, last_name, role, status, created_at) VALUES ( 'Igor','$2a$10$/w0FnOH8P8Bkfd6yMqFI8uWIut.Gn4InVdkLec0SFwfYw2NIA9mlS',
'Igor', 'Semenov', 'user', 'true', '21.02.2020');

INSERT INTO authority (authority, user_account_id) VALUES ('ROLE_ADMIN', '1');
INSERT INTO authority (authority, user_account_id) VALUES ('ROLE_USER', '2');
