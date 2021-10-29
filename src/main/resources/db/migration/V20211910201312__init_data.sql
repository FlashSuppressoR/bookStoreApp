-- INIT TABLE PUBLISHER --
INSERT INTO book_store.publisher  (id, name) VALUES ( 1 , 'Big Daddy');
INSERT INTO book_store.publisher  (id, name) VALUES ( 2 , 'Minsk prod' );
INSERT INTO book_store.publisher  (id, name) VALUES ( 3 , 'New Town' );

-- INIT TABLE GENRE --
INSERT INTO book_store.genre  (id, name) VALUES ( 1 , 'Fantasy');
INSERT INTO book_store.genre  (id, name) VALUES ( 2 , 'Horror' );
INSERT INTO book_store.genre  (id, name) VALUES ( 3 , 'Humor' );

-- INIT TABLE AUTHOR --
INSERT INTO book_store.author  (id, name) VALUES ( 1 , 'Bred Dee');
INSERT INTO book_store.author  (id, name) VALUES ( 2 , 'John Serb' );
INSERT INTO book_store.author  (id, name) VALUES ( 3 , 'Alex Green' );

-- INIT TABLE BOOK --
INSERT INTO book_store.book  (id, name, price, publisher_id, genre_id) VALUES ( 1 , 'Little Bee', 3.22 , 1, 1);
INSERT INTO book_store.book  (id, name, price, publisher_id, genre_id) VALUES ( 2 , 'Big system Black Sun', 2.33, 2, 1);
INSERT INTO book_store.book  (id, name, price, publisher_id, genre_id) VALUES ( 3 , 'Alex Green', 13.22, 1, 3);

-- INIT TABLE CUSTOMER --
INSERT INTO book_store.customer (id, name, email, password) VALUES ( 1 , 'Max', 'Max@com' , 'max');
INSERT INTO book_store.customer (id, name, email, password) VALUES ( 2 , 'Alex', 'Alex@com' , 'alex');
INSERT INTO book_store.customer (id, name, email, password) VALUES ( 3 , 'Rus', 'Rus@com' , 'rus');

-- INIT TABLE CART --
INSERT INTO book_store.cart (id,customer_id, book_id, book_counter) VALUES ( 1, 1, 2, 1);
INSERT INTO book_store.cart (id,customer_id, book_id, book_counter) VALUES ( 2, 3, 1, 2);
INSERT INTO book_store.cart (id,customer_id, book_id, book_counter) VALUES ( 3, 2, 3, 1);

-- INIT TABLE PURCHASE --
INSERT INTO book_store.purchase  (id, customer_id, purchase_time) VALUES ( 1 , 2, '2014-04-04 08:00:00');
INSERT INTO book_store.purchase  (id, customer_id, purchase_time) VALUES ( 2 , 3, '2014-04-04 08:00:00');
INSERT INTO book_store.purchase  (id, customer_id, purchase_time) VALUES ( 3 , 1, '2014-04-04 08:00:00');

-- INIT TABLE REVIEWS --
INSERT INTO book_store.review  (id, mark, comment, book_id) VALUES ( 1 , 5, 'Perfect book!', 1);
INSERT INTO book_store.review  (id, mark, comment, book_id) VALUES ( 2 , 3, 'So-so', 2);
INSERT INTO book_store.review  (id, mark, comment, book_id) VALUES ( 3 , 4, 'So cool', 3);

-- INIT TABLE BOOK_AUTHOR --
INSERT INTO book_store.book_author  (book_id, author_id) VALUES ( 1 , 1 );
INSERT INTO book_store.book_author  (book_id, author_id) VALUES ( 2 , 2 );
INSERT INTO book_store.book_author  (book_id, author_id) VALUES ( 3 , 3 );

COMMIT ;