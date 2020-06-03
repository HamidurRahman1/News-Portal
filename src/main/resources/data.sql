
insert into users (username, password, enabled) values
('user1', '$2b$10$jEDw4HHKDe75.mM47p4siui1XZrdGkf7Y0.oqKLY.Wt5TjIwuhByq', true),
('user2', '$2b$10$hS6mt4EUU38qu2jFz1nGp./KXkafrDZoWsAupfFrv7ZvwYiLnGVie', true),
('user3', '$2b$10$8Iv7IIaMNm6ZlFuyzZVoA.1dTC7kGBi5AXzH023tHFHHjGMQQoReO', true),
('user4', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true);

-- ROLE_ prefix will be added at runtime
insert into roles (role) values
('ADMIN'),
('PUBLISHER'),
('EDITOR'),
('USER');

insert into users_roles (user_id, role_id) values
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 2), (2, 4),
(3, 3), (3, 4),
(4, 4);

insert into authors (author_first_name, author_last_name) values
('Raj', 'Arnold'),
('Garin', 'Tanner'),
('Caleb', 'Phillips'),
('Izaan', 'Shaw'),
('Zack', 'Jennings');

insert into articles (article_title, article_body, article_publish_date) values
('title1', 'body1', '2020-05-20'),
('title2', 'body2', '2020-05-21'),
('title3', 'body3', '2020-05-22'),
('title4', 'body4', '2020-05-23');

insert into authors_articles (author_id, article_id) values
(5, 1), (3, 1), (1, 1),
(5, 2), (2, 2),
(1, 3), (3, 3),
(4, 4), (5, 4);

insert into comments (comment, article_id) values
('comment to article 1', 1), ('comment to article 1', 1), ('comment to article 1', 1), ('comment to article 1', 1),
('comment to article 2', 2), ('comment to article 2', 2), ('comment to article 2', 2),
('comment to article 3', 3), ('comment to article 3', 3),
('comment to article 4', 4);