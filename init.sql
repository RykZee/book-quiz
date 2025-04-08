CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    authorKey TEXT[],
    authorName TEXT[],
    title TEXT,
    firstPublishYear INTEGER,
    isbn TEXT[]
);

INSERT INTO books (authorKey, authorName, title, firstPublishYear, isbn) VALUES
(
    ARRAY['abc123'], 
    ARRAY['Fyodor Dostoevsky'],
    'Crime and Punishment',
    1866,
    ARRAY['fake-isbn']
);

INSERT INTO books (authorKey, authorName, title, firstPublishYear, isbn) VALUES
(
    ARRAY['def456'],
    ARRAY['Mark Twain'],
    'Tom Sawyer',
    1876,
    ARRAY['fake-isbn2']
);
