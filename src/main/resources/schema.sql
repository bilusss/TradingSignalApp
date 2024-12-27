CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    hash varchar(250) NOT NULL,
    credit FLOAT NOT NULL
    );

CREATE TABLE IF NOT EXISTS "Crypto" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(50) NOT NULL,
    description varchar(250) NOT NULL,
    logourl varchar(250) NOT NULL
);