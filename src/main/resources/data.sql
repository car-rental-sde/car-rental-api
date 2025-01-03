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
                   production_year, trunk_capacity, variant, brand_id, car_type_id, fuel_type_id) values
    (1, 5, 500, false, 'Mustang', 4, 4, 2004, 2000, null, 1, 6, 1),
    (2, 5.5, 400, true, 'Corvette', 4, 4, 2002, 2000, null, 2, 6, 1),
    (3, 6.5, 600, false, 'Viper', 4, 4, 2001, 2000, null, 3, 6, 1),
    (4, 7.5, 150, false, 'Grand Cherokee', 4, 4, 2003, 2000, null, 4, 4, 2),
    (5, 3.5, 80, true, '500', 4, 4, 2005, 2000, null, 5, 3, 1);

insert into car (id, day_price, addition_date, color, mileage, model_id) values
    (1, 20, '2024-01-01', 'Czerwony', 200000, 1),
    (2, 30, '2024-01-01', 'Niebieski', 300000, 2),
    (3, 40, '2024-01-01', 'Czerwony', 400000, 3),
    (4, 50, '2024-01-01', 'Czerwony', 500000, 4);

insert into equipment_2_car (car_id, equipment_id) values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 2),
    (2, 2);

insert into customer (id, bookly_id, is_blocked, name, surname) values
    (1, 20, false, 'Cristiano', 'Ronaldo'),
    (2, 22, false, 'Zygzak', 'McQueen'),
    (3, 25, true, 'Peter', 'Parker'),
    (4, 28, false, 'Robert', 'Lewandowski'),
    (5, 29, true, 'Fernando', 'Alonso');

insert into user_table (id, email, is_blocked, name, password, surname, username, role) values
    (1, 'example@example.com', false, 'user1', '$2a$10$8IoXLJvZF7CLX1pkH0XQoeF81jHracfkxo9p/jLsXfMmEdKlGVa6W', 'user1s', 'user', 'USER'),
    (2, 'test@test.com', false, 'api1', '$2a$10$X68Q8foGehOGC9.88FUlT.MJpptz.Q1HN.hEKXl2NhdCDPtV6KQyi', 'api1s', 'userapi', 'API_CLIENT');

insert into reservation (id, begin_date, begin_place, begin_position, details, end_date, end_place, end_position, is_maintenance,
                         car_id, customer_id) values
    (1, '2023-05-01', 'Mokotow', 'abc', null, '2023-05-05', 'Mokotow', 'abc', false, 1, 1),
    (2, '2020-01-05', 'Praga', 'abc', null, '2020-01-06', 'Aaa', 'abc', false, 2, 1),
    (3, '2020-01-08', 'Politechnika', 'abc', null, '2020-01-20', 'Praga', 'abc', true, 1, null),
    (4, '2023-01-20', 'Aaa', 'abc', null, '2023-01-27', 'Mokotow', 'abc', false, 3, 1),
    (5, '2023-01-27', 'Bbb', 'abc', null, '2023-01-29', 'Bbb', 'abc', true, 4, 1);

-- Restarting sequences for H2 database
ALTER TABLE brand ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM brand);
ALTER TABLE model ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM model);
ALTER TABLE car ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM car);
ALTER TABLE customer ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM customer);
ALTER TABLE user_table ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM user_table);
ALTER TABLE reservation ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM reservation);
