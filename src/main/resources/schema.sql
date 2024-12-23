CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    hash varchar(250) NOT NULL,
    credit FLOAT NOT NULL
    );
CREATE TABLE IF NOT EXISTS "Transactions" (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,--MAX 50 bytes
    userId INTEGER NOT NULL REFERENCES "User"(id),
    crytoIdBought VARCHAR(50) NOT NULL,--BTC
    cryptoIdSold VARCHAR(50) NOT NULL,--USDT
    amountBought DOUBLE PRECISION NOT NULL,
    amountSold DOUBLE PRECISION NOT NULL,
    completed_at TIMESTAMP NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description varchar(250) NOT NULL--MAX 250 bytes
    );