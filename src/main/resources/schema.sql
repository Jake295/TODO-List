CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(300),
--     complete TINYINT(1) NOT NULL DEFAULT 0,
    due_date DATE
    );