-- DELETE FROM car_type;
-- DELETE FROM fuel_type;
-- DELETE FROM equipment_piece;
-- DELETE FROM equipments2cars;
-- DELETE FROM brand;
-- DELETE FROM model;
-- DELETE FROM car;
-- DELETE FROM customer;
-- DELETE FROM user;
-- DELETE FROM reservation;

INSERT INTO car_type VALUES
    (1, 'Sedan'),
    (2, 'Compact'),
    (3, 'Micro car'),
    (4, 'Crossover'),
    (5, 'SUV'),
    (6, 'Sports car'),
    (7, 'Supercar'),
    (8, 'Estate Car'),
    (9, 'Limousine');

INSERT INTO fuel_type VALUES
    (1, 'Gasoline'),
    (2, 'Diesel'),
    (3, 'Electric'),
    (4, 'Hydrogen');

INSERT INTO equipment_piece VALUES
    (1, 'Air Control'),
    (2, 'GPS'),
    (3, 'Seat warmer'),
    (4, 'Cruise control');

INSERT INTO brand(id, country, logo_url, name) VALUES
    (1, 'US', '', 'Ford'),
    (2, 'US', '', 'Chevrolet'),
    (3, 'US', '', 'Dodge'),
    (4, 'US', '', 'Jeep'),
    (5, 'IT', '', 'Fiat'),
    (6, 'IT', '', 'Alfa Romeo'),
    (7, 'FR', '', 'Peugeot'),
    (8, 'FR', '', 'Citroen'),
    (9, 'FR', '', 'Renault'),
    (10, 'CZ', '', 'Skoda'),
    (11, 'ES', '', 'Seat'),
    (12, 'DE', '', 'Volkswagen'),
    (13, 'DE', '', 'BMW'),
    (14, 'DE', '', 'Audi'),
    (15, 'DE', '', 'Porsche'),
    (16, 'DE', '', 'Smart'),
    (17, 'DE', '', 'Mercedes-Benz'),
    (18, 'JP', '', 'Toyota'),
    (19, 'JP', '', 'Nissan'),
    (20, 'JP', '', 'Honda'),
    (21, 'JP', '', 'Lexus'),
    (22, 'KR', '', 'Hyundai'),
    (23, 'KR', '', 'Kia');

insert into model (id, avg_fuel_consumption, horse_power, is_gearbox_automatic, name, number_of_doors, number_of_seats,
                   production_year, trunk_capacity, brand_id, car_type_id, fuel_type_id) values
    (1, 5, 500, false, 'Mustang', 4, 4, 2004, 2000, 1, 6, 1),
    (2, 5.5, 400, true, 'Corvette', 4, 4, 2002, 2000, 2, 6, 1),
    (3, 6.5, 600, false, 'Viper', 4, 4, 2001, 2000, 3, 6, 1),
    (4, 7.5, 150, false, 'Grand Cherokee', 4, 4, 2003, 2000, 4, 4, 2),
    (5, 3.5, 80, true, '500', 4, 4, 2005, 2000, 5, 3, 1);

insert into car (id, day_price_euro, addition_date, color, mileage, model_id) values
    (1, 20, '2024-01-01', 'red', 200000, 1),
    (2, 30, '2024-01-01', 'blue', 300000, 2),
    (3, 40, '2024-01-01', 'black', 400000, 3),
    (4, 50, '2024-01-01', 'black', 500000, 4);

insert into equipment_2_car (car_id, equipment_id) values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 2),
    (2, 2);

insert into user_table (id, email, is_blocked, name, password, surname, username, role) values
    (1, 'example@example.com', false, 'user1', '$2a$10$8IoXLJvZF7CLX1pkH0XQoeF81jHracfkxo9p/jLsXfMmEdKlGVa6W', 'user1s', 'user', 'USER'),
    (2, 'test@test.com', false, 'api1', '$2a$10$X68Q8foGehOGC9.88FUlT.MJpptz.Q1HN.hEKXl2NhdCDPtV6KQyi', 'api1s', 'userapi', 'API_CLIENT');

insert into customer (id, external_id,  name, surname) values
    (1, 1, 'John', 'Doe');
--     (2, 2, 'Cristiano', 'McQueen'),
--     (3, 3, 'Peter', 'Parker'),
--     (4, 4, 'Robert', 'Lewandowski'),
--     (5, 5, 'Fernando', 'Alonso');

insert into reservation (id, begin_date, begin_position, end_date, end_position, is_maintenance,
                         car_id, cost, customer_id) values
    (1, '2025-01-06', '46.06706706749508,11.150431747739864', '2025-01-28', '46.0625771511208,11.115645328465872', false, 1, 450, 1);
--     (2, '2025-01-05', 'abc', '2020-01-06', 'abc', false, 2, 300, 1),
--     (3, '2025-01-08', 'abc', '2020-01-20', 'abc', true, 1, 200, null),
--     (4, '2025-01-20', 'abc', '2023-01-27', 'abc', false, 3, 100, 1),
--     (5, '2025-01-27', 'abc', '2023-01-29', 'abc', true, 4, 200, 1);

-- INSERT INTO book (id, name, page_count, author_id, content) VALUES (1, 'Effective Java', 416, 'author-1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore etdolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquipex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eufugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
-- INSERT INTO book (id, name, page_count, author_id, content) VALUES (2, 'Hitchhikers Guide to the Galaxy', 208, 'author-2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore etdolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquipex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eufugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');

INSERT INTO author (id, first_name, last_name) VALUES (1, 'Joshua', 'Bloch');
INSERT INTO author (id, first_name, last_name) VALUES (2, 'Douglas', 'Adams');
INSERT INTO author (id, first_name, last_name) VALUES (3, 'Bill', 'Bryson');

INSERT INTO book (id, name, page_count, author_id, content) VALUES (1, 'Effective Java', 416, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore etdolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquipex ea commodo consequat.');
INSERT INTO book (id, name, page_count, author_id, content) VALUES (2, 'Hitchhikers Guide to the Galaxy', 208, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore etdolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquipex ea commodo consequat.');

-- Restarting sequences for H2 database
ALTER TABLE brand ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM brand);
ALTER TABLE model ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM model);
ALTER TABLE car ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM car);
ALTER TABLE customer ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM customer);
ALTER TABLE user_table ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM user_table);
ALTER TABLE reservation ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM reservation);

ALTER TABLE book ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM book);
ALTER TABLE author ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM author);
