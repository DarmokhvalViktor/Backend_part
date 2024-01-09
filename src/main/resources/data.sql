-- Create table if not exists `roles`
CREATE TABLE IF NOT EXISTS `roles` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(90) UNIQUE
);

-- Insert 'ROLE_ADMIN' if it doesn't exist
INSERT IGNORE INTO `roles` (name) VALUES ('ROLE_ADMIN');

-- Insert 'USER' if it doesn't exist
INSERT IGNORE INTO `roles` (name) VALUES ('USER');