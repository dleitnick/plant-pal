BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, plant_reviews, comments, forums, posts, threads, forums_posts, posts_threads, thread_connections;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
    location varchar(100),
    skill_level INTEGER NOT NULL CHECK (skill_level >= 1 AND skill_level <= 3),
    email varchar(150) NOT NULL,
    created TIMESTAMP NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE plant_reviews (
    review_id SERIAL PRIMARY KEY,
    plant_id INTEGER NOT NULL,
    review_text TEXT,
    date_time TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
	CONSTRAINT FK_plant_reviews_username FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    plant_id INTEGER NOT NULL,
    comment_text TEXT NOT NULL,
    date_time TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
	CONSTRAINT FK_comments_username FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE virtual_gardens (
    user_id INTEGER NOT NULL,
    plant_id INTEGER NOT NULL,
    CONSTRAINT FK_vgp_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE forums (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE threads (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    date TIMESTAMP NOT NULL,
    title VARCHAR(255),
    message TEXT NOT NULL,
    upvotes INT DEFAULT 1,
    downvotes INT DEFAULT 0,
    delete_flag BOOLEAN DEFAULT false,
    is_post BOOLEAN DEFAULT false
);

CREATE TABLE forums_threads (
    forum_id INT REFERENCES forums(id),
    thread_id INT REFERENCES threads(id),
    PRIMARY KEY (forum_id, thread_id)
);

CREATE TABLE thread_connections (
    child_thread INT REFERENCES threads(id),
    parent_thread INT REFERENCES threads(id),
    PRIMARY KEY (child_thread, parent_thread)
);

COMMIT TRANSACTION;
