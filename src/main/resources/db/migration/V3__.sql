ALTER TABLE message
    DROP FOREIGN KEY FK_MESSAGE_ON_USER;

ALTER TABLE user
    ADD email VARCHAR(255) NULL;

ALTER TABLE user
    DROP COLUMN e_mail;

ALTER TABLE message
    DROP COLUMN user_id;