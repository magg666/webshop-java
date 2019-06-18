INSERT INTO departments
VALUES (1, 'Electronics');
INSERT INTO departments
VALUES (2, 'Books');
SELECT setval(pg_get_serial_sequence('departments', 'id'), coalesce(max(id), 0) + 1, false)
FROM departments;

INSERT INTO suppliers
VALUES (1, 'George R.R. Martin');
INSERT INTO suppliers
VALUES (2, 'Craig Shaefer');
INSERT INTO suppliers
VALUES (3, 'Danielle Steel');
INSERT INTO suppliers
VALUES (4, 'Nicolas Sparks');
INSERT INTO suppliers
VALUES (5, 'J. K. Rowling');
INSERT INTO suppliers
VALUES (6, 'Amazon');
INSERT INTO suppliers
VALUES (7, 'ASUS');
INSERT INTO suppliers
VALUES (8, 'Lenovo');
SELECT setval(pg_get_serial_sequence('suppliers', 'id'), coalesce(max(id), 0) + 1, false)
FROM suppliers;

INSERT INTO payments
VALUES (1, 'paypal');
INSERT INTO payments
VALUES (2, 'credit');
INSERT INTO payments
VALUES (3, 'transfer');
SELECT setval(pg_get_serial_sequence('payments', 'id'), coalesce(max(id), 0) + 1, false)
FROM payments;

INSERT INTO statuses
VALUES (1, 'saved');
INSERT INTO statuses
VALUES (2, 'checked');
INSERT INTO statuses
VALUES (3, 'paid');
INSERT INTO statuses
VALUES (4, 'confirmed');
INSERT INTO statuses
VALUES (5, 'shipped');
SELECT setval(pg_get_serial_sequence('statuses', 'id'), coalesce(max(id), 0) + 1, false)
FROM statuses;

INSERT INTO categories
VALUES (1, 'Tablet', 1);
INSERT INTO categories
VALUES (2, 'Laptop', 1);
INSERT INTO categories
VALUES (3, 'Router', 1);
INSERT INTO categories
VALUES (4, 'S-F', 2);
INSERT INTO categories
VALUES (5, 'Romance', 2);
INSERT INTO categories
VALUES (6, 'Teens', 2);
SELECT setval(pg_get_serial_sequence('categories', 'id'), coalesce(max(id), 0) + 1, false)
FROM categories;

INSERT INTO products
VALUES (1, 'Amazon Fire',
        'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 49.90, 'USD',
        1, 6);
INSERT INTO products
VALUES (2, 'Lenovo IdeaPad Mix 700',
        'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 479.00,
        'USD',
        1, 8);
INSERT INTO products
VALUES (3, 'Amazon Fire HD 8', 'Amazons latest Fire HD 8 tablet is a great value for media consumption.', 89.00, 'USD',
        1, 6);
INSERT INTO products
VALUES (4, 'A Game of Thrones', 'Dragons, love and incest', 6.62, 'USD', 4, 1);
INSERT INTO products
VALUES (5, 'Fire and Blood', 'A Targaryen History', 16.73, 'USD', 4, 1);
INSERT INTO products
VALUES (6, 'Ghosts of Gotham', 'Mystery and magic', 5.99, 'USD', 4, 2);
INSERT INTO products
VALUES (7, 'Lost and Found', 'Love novel', 2.66, 'USD', 5, 3);
INSERT INTO products
VALUES (8, 'Fairytale', 'Love novel again', 2.99, 'USD', 5, 3);
INSERT INTO products
VALUES (9, 'The Notebook', 'La La Land was based on this', 7.88, 'USD', 5, 4);
INSERT INTO products
VALUES (10, 'See me', 'Rich in emotion and fueled with suspense', 8.16, 'USD', 5, 4);
INSERT INTO products
VALUES (11, 'Harry Potter', 'The Illustrated Collection', 69.15, 'USD', 6, 5);
INSERT INTO products
VALUES (12, 'ASUS Vivobook K570ZD', 'Full Hd', 493.37, 'USD', 2, 7);
INSERT INTO products
VALUES (13, 'ASUS Whole Home Dual-Band AiMesh Router', 'Mesh Wifi System', 134.99, 'USD', 3, 7);
INSERT INTO products
VALUES (14, 'ASUS C302CA-DHM4 Chromebook Flip', '12.5-inch Touchscreen', 465.00, 'USD', 2, 7);
SELECT setval(pg_get_serial_sequence('products', 'id'), coalesce(max(id), 0) + 1, false)
FROM products;