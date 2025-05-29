-- 1. Create the database if not exists
CREATE DATABASE IF NOT EXISTS crushpredictordb;

-- 2. Use the database
USE crushpredictordb;

-- 3. Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- 4. Create predictions table
CREATE TABLE IF NOT EXISTS predictions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    your_name TEXT NOT NULL,
    crush_name TEXT NOT NULL,
    match_percentage INT NOT NULL,
    result_message TEXT NOT NULL,
    prediction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Insert a test user for login
INSERT IGNORE INTO users (username, password) VALUES ('test', 'test123');

-- Optional: Insert some sample predictions for testing
INSERT IGNORE INTO predictions (your_name, crush_name, match_percentage, result_message)
VALUES
    ('Jillian', 'Erwin', 94, '🔥 Perfect match!'),
    ('Jillian', 'Matthew', 73, '💞 Maybe give it a shot?'),
    ('Jillian', 'Gi', 84, '💖 Strong connection.');

-- Optional: Show results
SELECT * FROM predictions;