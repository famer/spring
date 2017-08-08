DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    "first_name" VARCHAR(255),
    "last_name" VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'ROLE_USER'
);

insert into users(username, password, role) values('famer', '$2a$10$4Hh1UObv7gUZxgojEdKxl.HOx1TDf3F4Pp7OrSqDCCXHAERyCOw5C', 'ROLE_ADMIN');
insert into users(username, password, role) values('fa', '$2a$10$IoQ4x5oEAqjR1v/bCQHWOepp.QtpCBCDZZFbMi6BrVWwN.tXNKtJK', 'ROLE_USER');

DROP TABLE IF EXISTS topics CASCADE;
CREATE TABLE topics
(
    id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES users (id),
    title VARCHAR(255) NOT NULL,
    last_updated timestamp DEFAULT current_timestamp 
);
insert into topics(user_id, title) values(1, 'title1');

DROP TABLE IF EXISTS posts CASCADE;
CREATE TABLE posts
(
    id SERIAL PRIMARY KEY,
    topic_id integer NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
    user_id integer NOT NULL REFERENCES users (id),
    content VARCHAR(255) NOT NULL,
    date timestamp DEFAULT current_timestamp 
);
insert into posts(topic_id, user_id, content) values(1, 1, 'content');
