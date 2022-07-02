--/**
-- * Schema for Payment Gateway Database
-- */

CREATE TABLE `card_info` (
    `id` uuid PRIMARY KEY ,
    `card_number` VARCHAR(19),
    `expiry_month` SMALLINT NOT NULL,
    `expiry_year` SMALLINT NOT NULL,
    `cvv` VARCHAR(4) NOT NULL,
    `name_on_card` VARCHAR(30) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE `payments` (
    `id` uuid PRIMARY KEY,
    `card_id` UUID NOT NULL,
    `amount` DECIMAL(20, 2) NOT NULL,
    `merchant_id` BIGINT NOT NULL,
    `currency` VARCHAR(3) NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    `description` VARCHAR(256),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_card_info foreign key(card_id) references card_info(id)
);


CREATE TABLE `merchants` (
    `id` BIGINT PRIMARY KEY,
    `api_key` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);