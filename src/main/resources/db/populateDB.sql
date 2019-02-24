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
(100000,'2019-02-22 10:01:00', 'Завтрак', 500),
(100000,'2019-02-22 13:02:00', 'Обед', 500),
(100000,'2019-02-22 17:03:00', 'Ужин', 510),
(100001,'2019-02-22 10:04:00', 'Завтрак', 1500),
(100001,'2019-02-22 14:05:00', 'Обед', 500);


