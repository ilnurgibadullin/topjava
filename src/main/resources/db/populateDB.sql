DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2020-02-23 08:00:00', 'Завтрак', 1200),
  (100000, '2020-02-24 12:00:00', 'Обед', 1000),
  (100000, '2020-02-24 19:00:00', 'Ужин', 1700),
  (100001, '2020-02-23 12:30:00', 'Обед', 1500),
  (100001, '2020-02-23 23:00:00', 'Ночной жор', 2000);