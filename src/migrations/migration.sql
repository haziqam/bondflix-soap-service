CREATE TABLE IF NOT EXISTS log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client VARCHAR(255),
    body_request TEXT,
    endpoint VARCHAR(255),
    ip VARCHAR(255)
);
