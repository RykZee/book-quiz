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
