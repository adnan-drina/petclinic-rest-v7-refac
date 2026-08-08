INSERT INTO types (id, name) VALUES (1, 'cat');
INSERT INTO types (id, name) VALUES (2, 'dog');
INSERT INTO types (id, name) VALUES (3, 'lizard');
INSERT INTO types (id, name) VALUES (4, 'snake');
INSERT INTO types (id, name) VALUES (5, 'bird');
INSERT INTO types (id, name) VALUES (6, 'hamster');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES (1, 'Leo', DATE '2010-09-07', 1, 1);
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES (2, 'Basil', DATE '2012-08-06', 6, 2);
-- Slice-two Visit seeds: fixed dates (never LocalDate.now()) so G-4 GET is not INCONCLUSIVE-by-clock
INSERT INTO visits (id, pet_id, visit_date, description) VALUES (1, 1, DATE '2013-01-01', 'rabies shot');
INSERT INTO visits (id, pet_id, visit_date, description) VALUES (2, 2, DATE '2013-01-02', 'rabies shot');
