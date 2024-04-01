CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN') DEFAULT 'ROLE_USER',
    UNIQUE KEY `uniq_username` (`username`),
    UNIQUE KEY `uniq_email` (`email`)
);

CREATE TABLE question_type(
  question_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  type ENUM('ONE_ANSWER', 'MULTIPLE_ANSWERS', 'FILL_BLANK') NOT NULL DEFAULT 'ONE_ANSWER'
);

CREATE TABLE worksheets (
    worksheet_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(127) NOT NULL,
    test_instruction VARCHAR(255) NOT NULL,
    class_year VARCHAR(50) NOT NULL,
    subject VARCHAR(50) NOT NULL
);

CREATE TABLE sentences (
    sentence_id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(400) NOT NULL,
    worksheet_id INT,
    question_type_id BIGINT,
    FOREIGN KEY (worksheet_id) REFERENCES worksheets(worksheet_id) ON DELETE CASCADE,
    FOREIGN KEY (question_type_id) REFERENCES question_type(question_type_id)
);

CREATE TABLE answers (
    answer_id INT AUTO_INCREMENT PRIMARY KEY,
    answer VARCHAR(150) NOT NULL,
    is_correct TINYINT(1),
    sentence_id INT,
    FOREIGN KEY (sentence_id) REFERENCES sentences(sentence_id) ON DELETE CASCADE
);

CREATE TABLE rating (
    rating_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    worksheet_id INT,
    rating DOUBLE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (worksheet_id) REFERENCES worksheets(worksheet_id) ON DELETE CASCADE
);


INSERT IGNORE INTO users (username, email, password, role)
SELECT 'admin', 'admin@example.com', '$2a$10$UsdwJ9mXkyNPMjE7C5bzguVGz3oCnFcy3SwFjvhHadgmRvNgbRGV2', 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE
    username = 'admin',
    email = 'admin@example.com',
    password = '$2a$10$UsdwJ9mXkyNPMjE7C5bzguVGz3oCnFcy3SwFjvhHadgmRvNgbRGV2',
    role = 'ROLE_ADMIN';

INSERT INTO question_type (type)
VALUES ('ONE_ANSWER'), ('MULTIPLE_ANSWERS'), ('FILL_BLANK');

-- Populate worksheets
INSERT INTO worksheets (title, test_instruction, class_year, subject)VALUES
     ('Literature Quiz', 'Test your knowledge of famous literary quotes!', '10', 'Literature'),
     ('Grammar Test', 'Assess your grammar skills with this comprehensive test.', '9', 'English'),
     ('Vocabulary Challenge', 'Expand your vocabulary by identifying synonyms and antonyms.', '11', 'Language Arts');

-- Populate sentences
-- Worksheet 1 - Literature Quiz
INSERT INTO sentences (content, worksheet_id, question_type_id) VALUES
    ('In a hole in the ground there lived a hobbit.', 1, 1),
    ('Not all who wander are lost.', 1, 1),
    ('Two households, both alike in dignity, In fair Verona, where we lay our scene.', 1, 1),
    ('What is the moral of the story?', 1, 3),
    ('Who is the author of this passage?', 1, 3);

-- Worksheet 2 - Grammar Test
INSERT INTO sentences (content, worksheet_id, question_type_id) VALUES
    ('What is the capital of France?', 2, 1),
    ('Who wrote "To Kill a Mockingbird"?', 2, 1),
    ('Complete the sentence: "The quick brown fox jumps over the..."', 2, 3),
    ('Choose the correct article: ___ apple', 2, 2),
    ('Identify the verb in the sentence: She is singing.', 2, 2);

-- Worksheet 3 - Vocabulary Challenge
INSERT INTO sentences (content, worksheet_id, question_type_id) VALUES
    ('The best way to find yourself is to lose yourself in the service of others.', 3, 1),
    ('The only thing we have to fear is fear itself.', 3, 1),
    ('The pen is mightier than the sword.', 3, 1),
    ('What is the theme of the passage?', 3, 3),
    ('Who is the author of this passage?', 3, 3);

-- Populate answers
-- Answers for Worksheet 1 - Literature Quiz
INSERT INTO answers (answer, is_correct, sentence_id) VALUES
  ('True', 1, 1),
  ('False', 0, 1),
  ('True', 1, 2),
  ('False', 0, 2),
  ('True', 1, 3),
  ('False', 0, 3),
  ('The moral is to always be kind to others.', 1, 4),
  ('The moral is to never judge a book by its cover.', 0, 4),
  ('J.R.R. Tolkien', 1, 5),
  ('George Orwell', 0, 5);

-- Answers for Worksheet 2 - Grammar Test
INSERT INTO answers (answer, is_correct, sentence_id) VALUES
  ('Paris', 1, 6),
  ('London', 0, 6),
  ('Harper Lee', 1, 7),
  ('J.K. Rowling', 0, 7),
  ('lazy dog', 1, 8),
  ('lazy cat', 0, 8),
  ('an', 1, 9),
  ('a', 0, 9),
  ('is', 0, 10),
  ('singing', 1, 10);

-- Answers for Worksheet 3 - Vocabulary Challenge
INSERT INTO answers (answer, is_correct, sentence_id) VALUES
  ('Friendship', 1, 11),
  ('Courage', 0, 11),
  ('J.R.R. Tolkien', 1, 12),
  ('George Orwell', 0, 12);