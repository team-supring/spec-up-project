INSERT INTO users (username, password, email, role, region_id, created_at)
SELECT 'alice', '$2a$10$AbCdEfGhIjKlMnOpQrStuv', 'alice@example.com', 'ROLE_USER', 1, NOW()
WHERE NOT EXISTS (
  SELECT 1 FROM users WHERE username = 'alice'
);

INSERT INTO users (username, password, email, role, region_id, created_at)
SELECT 'bob', '$2a$10$XyZaBcDeFgHiJkLmNoPqrs', 'bob@example.com', 'ROLE_USER', 1, NOW()
WHERE NOT EXISTS (
  SELECT 1 FROM users WHERE username = 'bob'
);
