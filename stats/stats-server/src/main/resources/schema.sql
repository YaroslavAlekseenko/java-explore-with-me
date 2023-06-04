DROP TABLE IF EXISTS statistics CASCADE;

CREATE TABLE IF NOT EXISTS statistics
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(255) NOT NULL,
    uri VARCHAR(255) NOT NULL,
    ip VARCHAR(255)  NOT NULL,
    timestamp        TIMESTAMP WITHOUT TIME ZONE NOT NULL
);