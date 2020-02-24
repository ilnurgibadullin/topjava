DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2020-02-23 08:00:00', 'Завтрак', 300),
  (100000, '2020-02-24 12:00:00', 'Обед', 1000),
  (100001, '2020-02-23 12:30:00', 'Обед', 1500);