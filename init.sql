CREATE TYPE transaction_type AS ENUM ('Deposit', 'Withdraw', 'Transfer');
CREATE CAST (VARCHAR AS transaction_type) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    CPF VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    user_CPF VARCHAR(255) UNIQUE REFERENCES users(CPF) ON DELETE CASCADE,
    balance INT DEFAULT 0 NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id) ON DELETE CASCADE,
    type transaction_type NOT NULL,
    amount INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
