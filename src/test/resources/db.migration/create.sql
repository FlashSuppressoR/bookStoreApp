drop schema if exists book_store cascade;
CREATE SCHEMA IF NOT EXISTS BOOK_STORE;

CREATE TABLE IF NOT EXISTS book_store.publisher
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_store.genre
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_store.book
(
    id           BIGINT(12)    NOT NULL AUTO_INCREMENT,
    name         VARCHAR(64)   NOT NULL,
    price        DECIMAL(5, 2) NOT NULL,
    publisher_id INT           NOT NULL,
    genre_id     INT           NOT NULL,
    amount       INT           NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_book_publisher
        FOREIGN KEY (publisher_id)
            REFERENCES book_store.publisher (id),
    CONSTRAINT fk_book_genre
        FOREIGN KEY (genre_id)
            REFERENCES book_store.genre (id)
);

CREATE TABLE IF NOT EXISTS book_store.review
(
    id      INT          NOT NULL AUTO_INCREMENT,
    mark    INT          NULL DEFAULT NULL,
    comment VARCHAR(255) NULL DEFAULT 'No comment',
    book_id BIGINT(12)   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_review_book
        FOREIGN KEY (book_id)
            REFERENCES book_store.book (id)
);

CREATE TABLE IF NOT EXISTS book_store.customer
(
    id       INT         NOT NULL AUTO_INCREMENT,
    name     VARCHAR(32) NOT NULL,
    email    VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_store.author
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_store.cart
(
    id           INT        NOT NULL AUTO_INCREMENT,
    customer_id  INT        NOT NULL,
    book_id      BIGINT(12) NOT NULL,
    book_counter INT        NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_cart_customer
        FOREIGN KEY (customer_id)
            REFERENCES book_store.customer (id),
    CONSTRAINT fk_cart_book
        FOREIGN KEY (book_id)
            REFERENCES book_store.book (id)
);

CREATE TABLE IF NOT EXISTS book_store.purchase
(
    id            INT       NOT NULL AUTO_INCREMENT,
    customer_id   INT       NOT NULL,
    purchase_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_purchase_user
        FOREIGN KEY (customer_id)
            REFERENCES book_store.customer (id)
);

CREATE TABLE IF NOT EXISTS book_store.book_author
(
    book_id   BIGINT(12) NOT NULL,
    author_id INT        NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_has_author_book
        FOREIGN KEY (book_id)
            REFERENCES book_store.book (id),
    CONSTRAINT fk_book_has_author_author
        FOREIGN KEY (author_id)
            REFERENCES book_store.author (id)
);

COMMIT;