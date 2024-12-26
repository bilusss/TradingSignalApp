CREATE TABLE IF NOT EXISTS "Users" (
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    hash varchar(250) NOT NULL,
    credit FLOAT NOT NULL
    );
CREATE TABLE IF NOT EXISTS "Crypto" (
    id SERIAL PRIMARY KEY,
    symbol VARCHAR(50) NOT NULL,--MAX 50 bytes
    name VARCHAR(50) NOT NULL,--MAX 50 bytes
    description varchar(250) NOT NULL,--MAX 250 bytes
    logourl varchar(250) NOT NULL--MAX 250 bytes
);
CREATE TABLE IF NOT EXISTS "Transactions" (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,--MAX 50 bytes
    userId INTEGER NOT NULL,
    crytoIdBought INTEGER NOT NULL,--BTC
    cryptoIdSold INTEGER NOT NULL,--USDT
    amountBought DOUBLE PRECISION NOT NULL,
    amountSold DOUBLE PRECISION NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description varchar(250) NOT NULL--MAX 250 bytes
    );
