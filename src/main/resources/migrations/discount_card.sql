CREATE TABLE discount_card
(
    id SERIAL PRIMARY KEY,
    discount INT NOT NULL,
    code VARCHAR(4) UNIQUE NOT NULL
);