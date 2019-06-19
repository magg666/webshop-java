INSERT INTO departments
VALUES (1, 'Electronics');
INSERT INTO departments
VALUES (2, 'Books');
SELECT setval(pg_get_serial_sequence('departments', 'id'), coalesce(max(id), 0) + 1, false)
FROM departments;

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