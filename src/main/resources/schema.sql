CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    hash varchar(250) NOT NULL,
    credit FLOAT NOT NULL
    );