CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE processing_log (
                                id UUID PRIMARY KEY,
                                user_id UUID NOT NULL,
                                input_text TEXT,
                                output_text TEXT,
                                created_at TIMESTAMP,
                                CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);