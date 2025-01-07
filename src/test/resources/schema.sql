CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    hash varchar(250) NOT NULL
    );

CREATE TABLE IF NOT EXISTS "Transactions" (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    userId INTEGER NOT NULL,
    cryptoIdBought INTEGER NOT NULL,
    cryptoIdSold INTEGER NOT NULL,
    amountBought DOUBLE PRECISION NOT NULL,
    amountSold DOUBLE PRECISION NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description varchar(250) NOT NULL
    );
