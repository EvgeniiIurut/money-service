DROP TABLE IF EXISTS account, transfer;

CREATE TABLE account
(
    id      BIGSERIAL PRIMARY KEY,
    balance DECIMAL(10, 4) NOT NULL,
    version BIGINT         NOT NULL DEFAULT 0
);

CREATE TABLE transfer
(
    id              BIGSERIAL PRIMARY KEY,
    from_account_id BIGINT         NOT NULL REFERENCES account (id),
    to_account_id   BIGINT REFERENCES account (id),
    amount          DECIMAL(10, 4) NOT NULL
);

INSERT INTO account(balance)
VALUES (10000.0),
       (10000.0),
       (10000.0);
