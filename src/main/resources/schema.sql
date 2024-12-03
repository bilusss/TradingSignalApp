CREATE TABLE IF NOT EXISTS User (
    id INT NOT NULL,
    username varchar(250) NOT NULL,
    hash varchar(250) NOT NULL,
    credit DOUBLE NOT NULL,
    PRIMARY KEY (id),
    );