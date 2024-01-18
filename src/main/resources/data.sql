# CREATE TABLE IF NOT EXISTS users (
#     id INT PRIMARY KEY AUTO_INCREMENT,
#     username VARCHAR(200) UNIQUE NOT NULL,
#     email VARCHAR(200) UNIQUE NOT NULL,
#     password varchar(200) NOT NULL,
#     role VARCHAR(100) NOT NULL
# );

# INSERT INTO users (name, username, year_of_birth, email, password, role)
# VALUES ('admin', 'admin', 1995, 'admin@gmail.com', 'admin_password', 'ADMIN');

-- Create table if not exists `roles`
# CREATE TABLE IF NOT EXISTS `roles` (
#     id INT PRIMARY KEY AUTO_INCREMENT,
#     name VARCHAR(90) UNIQUE NOT NULL
# );
#
# -- Insert 'ROLE_ADMIN' if it doesn't exist
# INSERT IGNORE INTO `roles` (name) VALUES ('ROLE_ADMIN');
#
# -- Insert 'USER' if it doesn't exist
# INSERT IGNORE INTO `roles` (name) VALUES ('USER');
#

#
#


# INSERT INTO users (name, username, year_of_birth, email, password, role)
# VALUES
#     ('John Doe', 'john.doe', 1990, 'john.doe@example.com', 'password123', 'USER'),
#     ('Jane Smith', 'jane.smith', 1985, 'jane.smith@example.com', 'securepass', 'ADMIN'),
#     ('Bob Johnson', 'bob.johnson', 1992, 'bob.johnson@example.com', 'mysecretpassword', 'USER');
