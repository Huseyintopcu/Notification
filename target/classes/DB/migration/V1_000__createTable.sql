CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification_types (
    id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);

CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    notification_type_id INT REFERENCES notification_types(id),
    message TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'pending', -- Bildirim durumu (pending, sent, failed gibi)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent_at TIMESTAMP
);

CREATE TABLE notification_settings (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    notification_type_id INT REFERENCES notification_types(id),
    enabled BOOLEAN DEFAULT TRUE -- Kullanıcının bu tür bildirimi alıp almayacağını belirtir
);