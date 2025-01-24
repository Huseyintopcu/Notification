ALTER TABLE users
ADD COLUMN created_by INT,
ADD COLUMN updated_by INT,
ADD COLUMN deleted_by INT,
ADD COLUMN deleted_at TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN version INT DEFAULT 1;

ALTER TABLE notification_types
ADD COLUMN created_by INT,
ADD COLUMN updated_by INT,
ADD COLUMN deleted_by INT,
ADD COLUMN deleted_at TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN version INT DEFAULT 1;

ALTER TABLE notifications
ADD COLUMN created_by INT,
ADD COLUMN updated_by INT,
ADD COLUMN deleted_by INT,
ADD COLUMN deleted_at TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN version INT DEFAULT 1;

ALTER TABLE notification_settings
ADD COLUMN created_by INT,
ADD COLUMN updated_by INT,
ADD COLUMN deleted_by INT,
ADD COLUMN deleted_at TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN version INT DEFAULT 1;

ALTER TABLE settings
ADD COLUMN created_by INT,
ADD COLUMN updated_by INT,
ADD COLUMN deleted_by INT,
ADD COLUMN deleted_at TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN version INT DEFAULT 1;
