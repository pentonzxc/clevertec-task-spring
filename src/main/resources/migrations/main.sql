CREATE SCHEMA clevertec_task;

CREATE TABLE product
(
    id    SERIAL PRIMARY KEY,
    price NUMERIC NOT NULL
);

CREATE TABLE discount_card
(
    id       SERIAL PRIMARY KEY,
    discount INT               NOT NULL,
    code     VARCHAR(4) UNIQUE NOT NULL
);


INSERT INTO product (price)
VALUES (2), (3), (5), (10), (1);

INSERT INTO discount_card (discount, code)
VALUES (15, 2222),
       (10, 3333),
       (20, 1239);
