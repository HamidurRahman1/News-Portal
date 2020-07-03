insert into users (username, password, enabled, first_name, last_name) values
('user1', '$2b$10$jEDw4HHKDe75.mM47p4siui1XZrdGkf7Y0.oqKLY.Wt5TjIwuhByq', true, 'first1', 'last1'),
('user2', '$2b$10$hS6mt4EUU38qu2jFz1nGp./KXkafrDZoWsAupfFrv7ZvwYiLnGVie', true, 'first2', 'last1'),
('user3', '$2b$10$8Iv7IIaMNm6ZlFuyzZVoA.1dTC7kGBi5AXzH023tHFHHjGMQQoReO', true, 'first3', 'last1'),
('user4', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true, 'first4', 'last1'),
('user5', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', false, 'first5', 'last5'),
('author1', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true, 'author1first', 'author1last'),
('author2', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true, 'author2first', 'author2last'),
('author3', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true, 'author3first', 'author3last'),
('author4', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true, 'author4first', 'author4last'),
('author5', '$2b$10$UmpwL1ImYyVTy1eofo8t9.ecIFzyyCJsML8y0k1UJtMWG2W1hJ3T6', true, 'author5first', 'author5last');

-- ROLE_ prefix will be added at runtime
insert into roles (role) values
('ADMIN'),
('PUBLISHER'),
('EDITOR'),
('AUTHOR'),
('USER');

insert into users_roles (user_id, role_id) values
(1, 1), (1, 2), (1, 3), (1, 5),
(2, 2), (2, 4), (2, 5),
(3, 3), (3, 4), (3, 5),
(4, 5),
(5, 5),
(6, 4), (6, 5),
(7, 4), (7, 5),
(8, 4), (8, 5),
(9, 4), (9, 5),
(10, 4), (10, 5);

insert into authors (author_id, user_id) values
(1, 6),
(2, 7),
(3, 8),
(4, 9),
(5, 10);

insert into articles (title, body, datetime, is_published) values
('title1', 'body1', '2020-05-20 20:20', true),
('title2', 'body2', '2020-05-21 20:20:20', true),
('title3', 'body3', '2020-05-22 20:20:20', true),
('title4', 'body4', '2020-05-23 20:20:20', true),
('no author article title11', 'no author article body1', '2020-05-24 20:20:20', true),
('no author article title1', 'no author article body2', '2020-05-25 20:20:20', false);

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