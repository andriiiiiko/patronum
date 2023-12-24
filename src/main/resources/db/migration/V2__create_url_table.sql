CREATE TABLE urls
(
    id              SERIAL PRIMARY KEY,
    user_id         BIGINT       NOT NULL,
    original_url    VARCHAR(255) NOT NULL,
    short_url       VARCHAR(255) NOT NULL,
    date_created    TIMESTAMP    NOT NULL,
    expiration_date TIMESTAMP,
    visit_count     INT          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);