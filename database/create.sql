DROP DATABASE IF EXISTS banking;
CREATE DATABASE banking;
USE banking;

CREATE USER 'dbAdmin'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON banking.* TO 'dbAdmin'@'%';

CREATE TABLE APP_USER (
  id int NOT NULL,
  username varchar(40) NOT NULL,
  first_name varchar(40) NOT NULL,
  last_name varchar(40) NOT NULL,
  phone_number char(10) NOT NULL,
  password varchar(40) NOT NULL,
  main_account int DEFAULT NULL,
  enabled bool NOT NULL DEFAULT true,
  PRIMARY KEY (id),
  UNIQUE KEY app_user_u_username (username),
  UNIQUE KEY app_user_u_phone_number (phone_number),
  KEY app_user_fk_main_account (main_account),
  CONSTRAINT app_user_chk_phone_number CHECK (((phone_number like '07%') and regexp_like(phone_number,'^[[:digit:]]{10}$')))
);

CREATE TABLE BANK_ACCOUNT (
  id int NOT NULL,
  owner_id int NOT NULL,
  creation_date date NOT NULL,
  currency varchar(6) NOT NULL,
  sold float NOT NULL,
  enabled bool NOT NULL DEFAULT true,
  PRIMARY KEY (id),
  KEY bank_account_fk_owner_id (owner_id),
  CONSTRAINT bank_account_fk_owner_id FOREIGN KEY (owner_id) REFERENCES APP_USER (id),
  CONSTRAINT bank_account_chk_currency CHECK ((currency in ('euro', 'ron', 'dollar'))),
  CONSTRAINT bank_account_chk_sold CHECK ((sold >= 0))
);

ALTER TABLE APP_USER ADD CONSTRAINT app_user_fk_main_account FOREIGN KEY (main_account) REFERENCES BANK_ACCOUNT (id);

CREATE TABLE TRANSACTION (
  source_account int NOT NULL,
  target_account int NOT NULL,
  transaction_timestamp datetime NOT NULL,
  amount float NOT NULL,
  transaction_status varchar(10) NOT NULL,
  PRIMARY KEY (source_account, target_account, transaction_timestamp),
  KEY transaction_fk_target_account (target_account),
  CONSTRAINT transaction_fk_source_account FOREIGN KEY (source_account) REFERENCES BANK_ACCOUNT (id),
  CONSTRAINT transaction_fk_target_account FOREIGN KEY (target_account) REFERENCES BANK_ACCOUNT (id),
  CONSTRAINT transaction_chk_amount CHECK ((amount > 10)),
  CONSTRAINT transaction_chk_transaction_status CHECK ((transaction_status in ('accepted', 'declined', 'waiting'))),
  CONSTRAINT transaction_chk_valid_transaction CHECK ((source_account <> target_account))
);

CREATE TABLE FRIEND_RELATION (
  friend_request_sender_id int NOT NULL,
  friend_request_receiver_id int NOT NULL,
  friend_request_status varchar(10) NOT NULL,
  PRIMARY KEY (friend_request_sender_id, friend_request_receiver_id),
  KEY friend_relation_fk_friend_request_receiver_id (friend_request_receiver_id),
  CONSTRAINT friend_relation_fk_friend_request_receiver_id FOREIGN KEY (friend_request_receiver_id) REFERENCES APP_USER (id),
  CONSTRAINT friend_relation_fk_friend_request_sender_id FOREIGN KEY (friend_request_sender_id) REFERENCES APP_USER (id),
  CONSTRAINT friend_relation_chk_friend_request_status CHECK ((friend_request_status in ('accepted', 'waiting', 'declined'))),
  CONSTRAINT friend_relation_chk_sender_receiver CHECK ((friend_request_sender_id <> friend_request_receiver_id))
);

DELIMITER ;;
CREATE FUNCTION account_is_owned_by_expected_user(account_id int, expected_user_id int) RETURNS bool
begin
    declare account_user_id int;
    
    select owner_id into account_user_id
    from BANK_ACCOUNT
    where id = account_id;

    if account_user_id = expected_user_id then
        return true;
    else
        return false;
    end if;
end ;;

CREATE FUNCTION count_reverse_relation(sender_id int, receiver_id int) RETURNS int
begin
	declare reverse_relation_count int;
	select count(*) into reverse_relation_count
	from FRIEND_RELATION
	where friend_request_receiver_id = sender_id and friend_request_sender_id = receiver_id;
    
    return reverse_relation_count;
end ;;

CREATE FUNCTION find_currency_factor(source_account int, target_account int) RETURNS float
begin
        declare source_currency varchar(6);
        declare target_currency varchar(6);

        select currency into source_currency
        from BANK_ACCOUNT
            where id = source_account;

        select currency into target_currency
        from BANK_ACCOUNT
            where id = target_account;

        if source_currency = target_currency then
            return 1.0;
        elseif source_currency = 'euro' then
            if target_currency = 'dollar' then
                return 1.19;
            else
                return 4.89;
            end if;
        elseif source_currency = 'dollar' then
            if target_currency = 'euro' then
                return 0.84;
            else
                return 4.10;
            end if;
        else
            if target_currency = 'euro' then
                return 0.20;
            else
                return 0.24;
            end if;
        end if;
end ;;

CREATE FUNCTION is_owner_enabled(owner_id int) RETURNS bool
begin
	declare owner_enabled boolean;
    select enabled into owner_enabled
    from APP_USER
    where id = owner_id;
    
    return owner_enabled;
end ;;


CREATE TRIGGER check_main_account_integrity_before_insert BEFORE INSERT ON APP_USER FOR EACH ROW 
begin
    if NEW.main_account is not NULL then
        signal sqlstate '45000' set message_text = 'Main account of user cannot be an account not owned by that user';
    end if;
end ;;

CREATE TRIGGER check_main_account_integrity_before_update BEFORE UPDATE ON APP_USER FOR EACH ROW 
begin
        if NEW.main_account is not NULL and not account_is_owned_by_expected_user(NEW.main_account, NEW.id) then
            signal sqlstate '45000' set message_text = 'Main account of user cannot be an account not owned by that user';
        end if;
end ;;

CREATE TRIGGER dissalow_disabling_users_with_money BEFORE UPDATE ON APP_USER FOR EACH ROW
begin
    declare sum_of_solds float;
    
    if not NEW.enabled then
		select sum(sold) into sum_of_solds
		from BANK_ACCOUNT
		where enabled and owner_id = NEW.id;
		
        if sum_of_solds > 0.5 then
			signal sqlstate '45003' set message_text = 'User still has bank accounts with money';
		end if;
    end if;
end ;;

CREATE TRIGGER disable_accounts_for_disbaled_user AFTER UPDATE ON APP_USER FOR EACH ROW
begin
	if not NEW.enabled then
		update BANK_ACCOUNT set enabled = false where owner_id = NEW.id;
    end if;
end ;;

CREATE TRIGGER disallow_new_accounts_for_disabled_user BEFORE INSERT ON BANK_ACCOUNT FOR EACH ROW
begin
    if not is_owner_enabled(NEW.owner_id) then
		signal sqlstate '45002' set message_text = 'Cannot create account for disabled user';
	end if;
end ;;

CREATE TRIGGER disallow_enabling_accounts_for_disabled_user BEFORE UPDATE ON BANK_ACCOUNT FOR EACH ROW
begin
	if not is_owner_enabled(NEW.owner_id) and NEW.enabled then
		signal sqlstate '45002' set message_text = 'Cannot enable accout of disabled user';
	end if; 
end ;;

CREATE TRIGGER disallow_disabling_of_accounts_with_money BEFORE UPDATE ON BANK_ACCOUNT FOR EACH ROW
begin
	declare bank_account_sold float;
	if not NEW.enabled then
		select sold into bank_account_sold
        from BANK_ACCOUNT
        where id = NEW.id;
        
        if NEW.sold > 0.5 then
			signal sqlstate '45003' set message_text = 'Bank account still has money';
		end if;
    end if;
end ;;

CREATE TRIGGER withdraw_money_for_new_transaction BEFORE INSERT ON TRANSACTION FOR EACH ROW
begin
    update BANK_ACCOUNT set sold = sold - NEW.amount where id = NEW.source_account;
end ;;

CREATE TRIGGER wire_money_to_target_after_insert AFTER INSERT ON TRANSACTION FOR EACH ROW
begin
	declare currency_factor float;
	if NEW.transaction_status = 'accepted' then
		set currency_factor = find_currency_factor(NEW.source_account, NEW.target_account);
		update BANK_ACCOUNT set sold = sold + currency_factor * NEW.amount where id = NEW.target_account;
	end if;
end ;;

CREATE TRIGGER wire_money_to_account_after_update AFTER UPDATE ON TRANSACTION FOR EACH ROW
begin
        declare currency_factor float;
        if OLD.transaction_status = 'waiting' and NEW.transaction_status = 'declined' then
            update BANK_ACCOUNT set sold = sold + OLD.amount where id = OLD.source_account;
        elseif OLD.transaction_status = 'waiting' and NEW.transaction_status = 'accepted' then
            set currency_factor = find_currency_factor(OLD.source_account, OLD.target_account);
            update BANK_ACCOUNT set sold = sold + currency_factor * OLD.amount where id = OLD.target_account;
        end if;
end ;;

CREATE TRIGGER check_for_unique_friend_relation_before_insert BEFORE INSERT ON FRIEND_RELATION FOR EACH ROW
begin
			if count_reverse_relation(NEW.friend_request_sender_id, NEW.friend_request_receiver_id) <> 0 then
				signal sqlstate '45001' set message_text = 'The reverse of the new friend relation already exists';
			end if;
end ;;

CREATE TRIGGER check_for_unique_friend_relation_before_update BEFORE UPDATE ON FRIEND_RELATION FOR EACH ROW
begin
			if count_reverse_relation(NEW.friend_request_sender_id, NEW.friend_request_receiver_id) <> 0
            and (OLD.friend_request_sender_id, OLD.friend_request_receiver_id) <> (NEW.friend_request_receiver_id, NEW.friend_request_sender_id) then
				signal sqlstate '45001' set message_text = 'The reverse of the new friend relation already exists';
			end if;
end ;;