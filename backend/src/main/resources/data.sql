-- Insert sample users
INSERT INTO users (name, username, email, password, phone, website)
VALUES
    ('Leanne Graham', 'Bret', 'Sincere@april.biz', '$2a$10$rDkPvvAFV6GgJkKq8XxKXe6XxKXe6XxKXe6XxKXe6XxKXe6XxKXe', '1-770-736-8031 x56442', 'hildegard.org'),
    ('Ervin Howell', 'Antonette', 'Shanna@melissa.tv', '$2a$10$rDkPvvAFV6GgJkKq8XxKXe6XxKXe6XxKXe6XxKXe6XxKXe6XxKXe', '010-692-6593 x09125', 'anastasia.net'),
    ('Clementine Bauch', 'Samantha', 'Nathan@yesenia.net', '$2a$10$rDkPvvAFV6GgJkKq8XxKXe6XxKXe6XxKXe6XxKXe6XxKXe6XxKXe', '1-463-123-4447', 'ramiro.info'),
    ('Patricia Lebsack', 'Karianne', 'Julianne.OConner@kory.org', '$2a$10$rDkPvvAFV6GgJkKq8XxKXe6XxKXe6XxKXe6XxKXe6XxKXe6XxKXe', '493-170-9623 x156', 'kale.biz'),
    ('Chelsey Dietrich', 'Kamren', 'Lucio_Hettinger@annie.ca', '$2a$10$rDkPvvAFV6GgJkKq8XxKXe6XxKXe6XxKXe6XxKXe6XxKXe6XxKXe', '(254)954-1289', 'demarco.info');

-- Insert addresses
INSERT INTO address (user_id, street, suite, city, zipcode, geo_lat, geo_lng)
VALUES
    (1, 'Kulas Light', 'Apt. 556', 'Gwenborough', '92998-3874', '-37.3159', '81.1496'),
    (2, 'Victor Plains', 'Suite 879', 'Wisokyburgh', '90566-7771', '-43.9509', '-34.4618'),
    (3, 'Douglas Extension', 'Suite 847', 'McKenziehaven', '59590-4157', '-68.6102', '-47.0653'),
    (4, 'Hoeger Mall', 'Apt. 692', 'South Elvis', '53919-4257', '29.4572', '-164.2990'),
    (5, 'Skiles Walks', 'Suite 351', 'Roscoeview', '33263', '-31.8129', '62.5342');

-- Insert companies
INSERT INTO company (user_id, name, catch_phrase, bs)
VALUES
    (1, 'Romaguera-Crona', 'Multi-layered client-server neural-net', 'harness real-time e-markets'),
    (2, 'Deckow-Crist', 'Proactive didactic contingency', 'synergize scalable supply-chains'),
    (3, 'Romaguera-Jacobson', 'Face to face bifurcated interface', 'e-enable strategic applications'),
    (4, 'Robel-Corkery', 'Multi-tiered zero tolerance productivity', 'transition cutting-edge web services'),
    (5, 'Keebler LLC', 'User-centric fault-tolerant solution', 'revolutionize end-to-end systems'); 