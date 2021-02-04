DROP DATABASE IF EXISTS banking;
CREATE DATABASE banking;
USE banking;

CREATE USER 'dbAdmin'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON banking.* TO 'dbAdmin'@'localhost';

CREATE TABLE APP_USER
  (
    user_id INT PRIMARY KEY,
    user_name VARCHAR(40) NOT NULL,
    first_name          VARCHAR(40) NOT NULL,
    last_name			VARCHAR(40) NOT NULL,
    phone_number CHAR(10) NOT NULL,
    user_password 		VARCHAR(40) NOT NULL,
    constraint u_user_name unique(user_name),
    constraint u_phone_number unique(phone_number),
    constraint chk_valid_phone_number CHECK (phone_number LIKE '07%'
											 AND phone_number regexp '^[[:digit:]]{10}$')
  );

CREATE TABLE BANK_ACCOUNT
  (
    account_id INT PRIMARY KEY,
    owner_id      INT NOT NULL,
    creation_date DATE NOT NULL,
    currency      VARCHAR(6) CHECK (currency IN ('euro', 'ron', 'dollar')) NOT NULL,
    sold FLOAT CHECK (sold   >= 0) NOT NULL,
    main_account BOOLEAN NOT NULL,
    CONSTRAINT Fk_owner FOREIGN KEY (owner_id)
    REFERENCES APP_USER(user_id)
  );

CREATE TABLE TRANSACTION
  (
    source_account      INT NOT NULL,
    target_account      INT NOT NULL,
    transaction_timestamp DATETIME NOT NULL,
    amount            FLOAT CHECK (amount > 10) NOT NULL,
    transaction_status	VARCHAR(10) CHECK(transaction_status IN ('accepted', 'declined', 'waiting')),
    CONSTRAINT Pk_transaction PRIMARY KEY(source_account, target_account, transaction_timestamp),
    CONSTRAINT Fk_source_account FOREIGN KEY (source_account)
    REFERENCES BANK_ACCOUNT(account_id),
    CONSTRAINT Fk_target_account FOREIGN KEY (target_account)
    REFERENCES BANK_ACCOUNT(account_id),
    CONSTRAINT Chk_valid_transaction CHECK (source_account <> target_account)
  );