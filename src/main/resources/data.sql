insert into users (username, password, enabled, first_name, last_name) values
('admin#1', '$2y$10$Cp9KRFlteaFiEWzRsundZ.W1difOh07H.bKy66G85Q.EAtV5kOhh.', true, 'Kavan', 'Connolly'), --- adminPass1#
('username#2', '$2y$10$O/C7hFr2LtZpGN/BfTqmleYUwiOPg6HkGFAcj8y2jo5MpCdCdD.fe', true, 'Mahima', 'Christensen'), --- usernamePass#2
('username#3', '$2y$10$bWxl0oHiI2Lr6rfCtcCgYeGlYZdDXUkdMsI6dTLIWqAFaxF83ry0y', true, 'Anjali', 'Brewer'), --- usernamePass#3
('username#4', '$2y$10$NQ6J7UZQbhWu3Ss3ih0xFe0HzOgmNh5U6zbuLNVq5X2DUGMxV59m2', true, 'Kendra', 'Avalos'), --- usernamePass#4
--- only users
('username#5', '$2y$10$dmOuMp9YtxURWpTqMTrgdO.Lebf2Kvno0Eosy5K.AiA42YHYrdfA6', true , 'Mylo', 'Gaines'), --- userPass#5
('username#6', '$2y$10$brO.1XeROgBW8UGyPspUkutx92/HmmouTvwxv4EdLGfh1lmfzOSji', true , 'Aairah', 'Fisher'), --- userPass#6
('username#7', '$2y$10$b.VDU.TOhgD6tL60sUtrqehBVJxkToYq3bxrs8V13YX1qixXJGstK', true , 'Vivaan', 'Mason'), --- userPass#7
--- user and authors
('userAsAuthor#1', '$2y$10$dtcp5t437.sVoddzZwZnjuJkVntNJDeRqO9YACkOtpJgNAxnjs/OG', true, 'Aadil', 'Wharton'), --- authorPass#8
('userAsAuthor#2', '$2y$10$xH.eJxQQG0HDR6Ux8BZ4TOcs4soUxrKcAMyZWN1nb5T86ynaLuGDu', true, 'Emyr', 'Odom'), --- authorPass#9
('userAsAuthor#3', '$2y$10$R1q4BZ5R0NjLBGSngLtylOstVI.rJrhGaln.0xLWCVi1MJaH0/AC2', true, 'Vincent', 'Wilkinson'), --- authorPass#10
('userAsAuthor#4', '$2y$10$IlzsaccEUrgJSm08tIXfvuLPKB6.Vu.BRZivnvpcE59/AoVE5a5VC', true, 'Fatimah', 'Mcnally'), --- authorPass#11
('userAsAuthor#5', '$2y$10$lrco4A.3wGFyXMH.fR7TJuN82KwodobudWgWecvAZgC2ZgOHaFoBi', true, 'Forrest', 'Rowley'); --- authorPass#12

-- ROLE_ prefix will be added at runtime
insert into roles (role) values
('ADMIN'),
('PUBLISHER'),
('EDITOR'),
('AUTHOR'),
('USER');

insert into users_roles (user_id, role_id) values
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 2), (2, 5),
(3, 3), (3, 4), (3, 5),
(4, 3), (4, 4), (4, 5),
--- only users
(5, 5),
(6, 5),
(7, 5),
--- Authors
(8, 4), (8, 5),
(9, 4), (9, 5),
(10, 4), (10, 5),
(11, 4), (11, 5),
(12, 4), (12, 5);

insert into articles (title, body, timestamp, is_published) values
('The 3 Levels of Self-Awareness', 'The fact is that the majority of our thoughts and actions are on autopilot. This isn’t necessarily a bad thing either. Our habits, routines, impulses, and reactions carry us through our lives so we don’t have to stop and think about it every time we wipe our ass or start a car. ...', '1594336982542', true),
('How to Let Go', '... I’m no stranger to loss. I don’t think any of us are. I’ve watched family members and friends die. I’ve had romantic relationships end in a spectacular explosion and I’ve had them end in a long, drawn out silence. I’ve lost friendships, jobs, cities, and communities. I’ve lost beliefs—in both myself and others. ...', '1594336982542', true),
('If Self-Discipline Feels Difficult, Then You’re Doing It Wrong', '... Most people think of self-discipline in terms of willpower. If we see someone who wakes up at 5 AM every day, eats an avocado-chia-fennel-apricot-papaya smoothie each meal, snorts brussel sprout flakes, and works out for three hours before even wiping their ass in the morning, we assume they’re achieving this through straight-up self-abuse—that there is some insatiable inner demon driving them like a slave to do everything right, no matter what. ...', '1594336982542', true),
('Five Mindsets that Create Success', '1. YOU ALWAYS HAVE A CHOICE, 2. ADOPT A BIAS TOWARDS ACTION, 3. LET GO OF THE NEED TO BE RIGHT, 4. SEE THE WORLD FOR WHAT IT IS, NOT FOR WHAT YOU WISH IT COULD BE, 5. DEFINE SUCCESS INTERNALLY, NOT EXTERNALLY', '1594336982542', true),
('Guide to Personal Values', '... Many of us state values we wish we had as a way to cover up the values we actually have. In this way, aspiration can often become another form of avoidance. Instead of facing who we really are, we lose ourselves in who we wish to become. ...', '1594336982542', true),
('The One Rule for Life', '... If you’re living in a democratic society that protects individual rights, you have Kant to partially thank for that. He was the first person to ever envision a global governing body that could guarantee peace across much of the world. He described space/time in such a way that it inspired Einstein’s discovery of relativity.3 He came up with the idea that animals could potentially have rights,4 invented the philosophy of aesthetics and beauty, and resolved a 200-year philosophical debate in the span of a couple hundred pages. ...', '1594336982542', true),
('Title coming soon', 'body coming soon', '1594336982542', false),
('Title coming soon', 'body coming soon', '1594336982542', false),
('Screw Finding Your Passion', 'Remember back when you were a kid? You would just do things. You never thought to yourself, “What are the relative merits of learning baseball versus football?” You just ran around the playground and played baseball and football. You built sand castles and played tag and asked silly questions and looked for bugs and dug up grass and pretended you were a sewer monster. ...', '1594336982542', true),
('Love Is Not Enough', '... When we believe that “all we need is love,” then like Lennon, we’re more likely to ignore fundamental values such as respect, humility and commitment towards the people we care about. After all, if love solves everything, then why bother with all the other stuff — all of the hard stuff? ...', '1594336982542', true);

insert into authors_articles (user_id, article_id) values
(8, 1), (10, 1), (12, 1),
(9, 2), (11, 2),
(3, 3), (10, 3),
(4, 4), (10, 4),
(8, 5), (9, 5), (11, 5),
(12, 6), (3, 6),
(9, 7), (11, 7),
(10, 8), (9, 8);

insert into comments (comment, article_id) values
('good', 1), ('great', 1), ('a nice one', 1), ('hPMMJVTqJD', 1),
('random TTnEyvLNJs', 2), ('vHYIWfeosL', 2),
('some texts', 3), ('gGGpzDXpmh', 3), ('kkiLNgpBOK', 3),
('kPhFCNeIWk', 4),
('fJGUliuNfJ', 5), ('YjbLAXRwiB', 5), ('YatFEUrlyc', 5), ('hPMMJVTqJD', 5),
('XGPOHaXXnJ', 6), ('NYNBbUwWks', 6),
('saCELauHHk', 7), ('JLhPnoudws', 7),
('mNZKwpOtex', 8);