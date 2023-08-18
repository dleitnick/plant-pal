BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role, location, skill_level, email, created) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Billings, MT', 2, 'user@plantpals.com', NOW());
INSERT INTO users (username,password_hash,role, skill_level, email, created) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 3, 'admin@plantpals.com', NOW());
INSERT INTO users (username, password_hash, role, skill_level, email, created)
VALUES
('daniel', '$2a$10$4ShFdfSs/vdvbaFx5O8/hODrMQ6o0zGARxdR0nQkddvKsIYHcb5J.', 'ROLE_USER', 2, 'daniel@leitnick.com', NOW()),
('joey', '$2a$10$TIjSWuhMVDEuCPlhPZkwHuTz55iBvisRkAepv6MrUhiVdd/g3uSri', 'ROLE_USER', 1, 'joey@joey.com', NOW()),
('blankeshiprm', '$2a$10$FzG46MTub7t2w7zNMqROgubKyYTunDUzRsuu4CTo4GOQeTS2U.KKm', 'ROLE_USER', 3, 'blankenship@blankenship.com', NOW());

INSERT INTO forums (name, description)
VALUES
('General Discussion', 'A forum to discuss just about anything!'),
('Help and Advice', 'A forum to get help and advice from other plant lovers!'),
('Photos', 'Post pictures of your beautiful plants!');

-- Temp Dummy Data
INSERT INTO users (username, password_hash, role, location, skill_level, email, created)
VALUES
('SunnyGardener', '7f8c497a6d5545228a8cdf805da5fcdc08e8a4f7', 'ROLE_USER', 'Los Angeles, CA', 2, 'SunnyGardener@gmail.com', NOW()),
('NewLeaf21', '3e2875a8b2ce5f86a4e4da248e382058f7a81550', 'ROLE_USER', 'Boston, MA', 1, 'NewLeaf21@gmail.com', NOW()),
('BugBuster', '2d45f7437f3a8c0a1eebfd0e9fae6a48501b564e', 'ROLE_USER', 'Houston, TX', 2, 'BugBuster@gmail.com', NOW()),
('GiftedGreens', 'be06a794a3152ebff1a295a7fa04d4553b62f1d4', 'ROLE_USER', 'Portland, OR', 3, 'GiftedGreens@gmail.com', NOW()),
('EveningBloomer', 'f4e69cbdfeda6a598bb9cf1a5205c9f5f90e3083', 'ROLE_USER', 'Nashville, TN', 2, 'EveningBloomer@gmail.com', NOW()),
('DesertDreamer', 'da275a01f5655b99fba7af36f2948d20f3b8a443', 'ROLE_USER', 'Phoenix, AZ', 1, 'DesertDreamer@gmail.com', NOW()),
('NatureMagnet', 'ab4c55c9df5a6a5f2af4e2a1f2b8e5591f7f925a', 'ROLE_USER', 'Orlando, FL', 1, 'NatureMagnet@gmail.com', NOW()),
('FlowerLover92', 'e0dc7e7df6551b93864e9fe4ae4073617c74c28b', 'ROLE_USER', 'San Francisco, CA', 2, 'FlowerLover92@gmail.com', NOW()),
('GreenThumbTX', '1c3028a7db507c9d4ac7b8c4b7dacf206b32dbf5', 'ROLE_USER', 'Austin, TX', 1, 'GreenThumbTX@gmail.com', NOW()),
('PlantNostalgia', 'dc89a5c567957d408d5c6772a1e4712879d34b8f', 'ROLE_USER', 'Seattle, WA', 2, 'PlantNostalgia@gmail.com', NOW()),
('CityPlanter', '4d76256b8df1e2d11a11cb9f8d8f6b5b4d1d94f7', 'ROLE_USER', 'New York, NY', 1, 'CityPlanter@gmail.com', NOW()),
('SkyGardener', '3c7d9f7ba5647e572c56a7c73f5a92d7a13d5d5d', 'ROLE_USER', 'Denver, CO', 1, 'SkyGardener@gmail.com', NOW()),
('BeachBotanist', '8c5b2a6f2e7d2c4a836480d3f8dc9b4e5f5a3c5c', 'ROLE_USER', 'Miami, FL', 2, 'BeachBotanist@gmail.com', NOW()),
('WindyCityFlora', 'bc34a5cd2b7f9531f6582845c6b0840e5779a7f5', 'ROLE_USER', 'Chicago, IL', 3, 'WindyCityFlora@gmail.com', NOW());

INSERT INTO plant_reviews (plant_id, review_text, date_time, location, username, rating)
VALUES
(101, 'Absolutely mesmerizing! The colors of the flowers captivate my heart every morning.', '2023-06-15 08:30:00', 'San Francisco, CA', 'FlowerLover92', 5),
(102, 'Grows slower than I anticipated. It''s not a fan of the partial shade in my backyard.', '2023-06-10 12:10:15', 'Austin, TX', 'GreenThumbTX', 3),
(103, 'Its scent reminds me of my childhood garden. Pure nostalgia!', '2023-06-08 14:42:50', 'Seattle, WA', 'PlantNostalgia', 4),
(104, 'Tricky to take care of. I think it needs a specific type of soil.', '2023-06-05 10:15:00', 'New York, NY', 'CityPlanter', 2),
(105, 'A beautiful addition to my balcony garden. Birds seem to love it too!', '2023-06-01 17:20:20', 'Denver, CO', 'SkyGardener', 5),
(106, 'Requires minimal care and looks splendid year-round. Highly recommend for beginners.', '2023-05-28 13:25:00', 'Miami, FL', 'BeachBotanist', 5),
(107, 'Not sure if I received a different variant, but mine doesn''t bloom as shown in the pictures.', '2023-05-20 09:08:00', 'Chicago, IL', 'WindyCityFlora', 3);

INSERT INTO comments (plant_id, comment_text, date_time, location, username)
VALUES
(201, 'I''ve had one of these for years! They thrive in sunny spots.', '2023-06-17 10:45:00', 'Los Angeles, CA', 'SunnyGardener'),
(202, 'Does anyone have tips for repotting this beauty?', '2023-06-16 14:05:15', 'Boston, MA', 'NewLeaf21'),
(203, 'Watch out for pests! Mine got infested pretty quickly.', '2023-06-15 12:35:45', 'Houston, TX', 'BugBuster'),
(204, 'I gifted this to my mom, and she adores it!', '2023-06-14 09:30:00', 'Portland, OR', 'GiftedGreens'),
(205, 'The blossoms smell heavenly in the evening.', '2023-06-13 18:20:20', 'Nashville, TN', 'EveningBloomer'),
(206, 'Struggling with mine. Any watering advice?', '2023-06-12 11:15:15', 'Phoenix, AZ', 'DesertDreamer'),
(207, 'This plant is a centerpiece in my garden. Birds and bees are drawn to it!', '2023-06-11 16:10:00', 'Orlando, FL', 'NatureMagnet');

-- INSERT INTO threads (user_id, date, title, message, is_post)
-- VALUES
-- (3, NOW(), 'Best Plants for Beginners', 'What are some great plants for beginners?', true), --id 1
-- (5, NOW(), 'Watering Tips', 'Share your watering tips here!', true),                        --id 2
-- (10, NOW(), 'Organic Gardening', 'Let''s discuss organic gardening practices.', true),      --id 3
-- (13, NOW(), 'Seasonal Flowers', 'What are your favorite seasonal flowers?', true),          --id 4
-- (11, NOW(), 'Soil Nutrition Tips', 'Discuss your favorite soil nutrients!', true),          --id 5
-- (12, NOW(), 'Indoor Plant Care', 'Share your indoor plant care routine!', true),            --id 6
-- (14, NOW(), 'Fruit Gardening', 'How to grow fruit in your backyard?', true),                --id 7
-- (9, NOW(), 'Herb Gardening', 'Share tips on growing herbs in pots!', true),                 --id 8
-- (15, NOW(), 'Landscaping Ideas', 'Looking for unique landscaping ideas.', true),            --id 9
-- (2, NOW(), 'Pest Control', 'How do you control pests organically?', true);                  --id 10

-- INSERT INTO forums_threads (forum_id, thread_id)
-- VALUES
-- (1, 1), -- General Discussion
-- (1, 2), -- General Discussion
-- (2, 3), -- Help/Assistance
-- (2, 4), -- Help/Assistance
-- (1, 5), -- General Discussion
-- (1, 6), -- General Discussion
-- (2, 7), -- Help/Assistance
-- (2, 8), -- Help/Assistance
-- (1, 9), -- General Discussion
-- (2, 10); -- Help/Assistance

-- INSERT INTO threads (user_id, date, message, is_post)
-- VALUES
-- (4, NOW(), 'I recommend starting with succulents!', false),     --id 11
-- (6, NOW(), 'Don''t water in the midday sun!', false),           --id 12
-- (7, NOW(), 'I love using compost in my garden.', false),        --id 13
-- (8, NOW(), 'Tulips and daffodils are my favorites.', false),    --id 14
-- (1, NOW(), 'I use organic compost!', false),                    --id 15
-- (3, NOW(), 'Spider plants are great for indoors.', false),      --id 16
-- (4, NOW(), 'Tomatoes need plenty of sunlight!', false),         --id 17
-- (7, NOW(), 'Basil and mint grow well together.', false),        --id 18
-- (5, NOW(), 'Use native plants for landscaping.', false),        --id 19
-- (6, NOW(), 'Neem oil works well against pests.', false);        --id 20

-- -- Connecting the replies to the top-level threads
-- INSERT INTO thread_connections (child_thread, parent_thread)
-- VALUES
-- (5, 1),
-- (6, 2),
-- (7, 3),
-- (8, 4),
-- (9, 5),
-- (10, 6),
-- (11, 7),
-- (12, 8),
-- (13, 9),
-- (14, 10);

-- -- Additional replies to the first level replies
-- INSERT INTO threads (user_id, date, message, is_post)
-- VALUES
-- (2, NOW(), 'Absolutely! Succulents are low-maintenance.', false),       --id 21
-- (4, NOW(), 'I agree, watering early morning is best.', false),          --id 22
-- (5, NOW(), 'Composting is the way to go!', false),                      --id 23
-- (3, NOW(), 'I love tulips too!', false),                                --id 24
-- (8, NOW(), 'I''ve heard of using coffee grounds for soil.', false),     --id 25
-- (12, NOW(), 'I''m trying bonsai with indoor plants.', false),           --id 26
-- (15, NOW(), 'I''m growing strawberries, any tips?', false),             --id 27
-- (11, NOW(), 'I''m using rosemary with my herbs.', false),               --id 28
-- (14, NOW(), 'Rock gardens are an interesting idea!', false),            --id 29
-- (13, NOW(), 'I use garlic spray against aphids.', false);               --id 30

-- -- Connecting additional replies to the first level replies
-- INSERT INTO thread_connections (child_thread, parent_thread)
-- VALUES
-- (15, 5),
-- (16, 6),
-- (17, 7),
-- (18, 8),
-- (19, 9),
-- (20, 10),
-- (21, 11),
-- (22, 12),
-- (23, 13),
-- (24, 14);

-- Creating top-level threads (posts)
INSERT INTO threads (user_id, date, title, message, is_post)
VALUES
(3, NOW(), 'Best Plants for Beginners', 'What are some great plants for beginners?', true),     --id: 1
(5, NOW(), 'Watering Tips', 'Share your watering tips here!', true),                            --id: 2
(10, NOW(), 'Organic Gardening', 'Let''s discuss organic gardening practices.', true),          --id: 3
(13, NOW(), 'Seasonal Flowers', 'What are your favorite seasonal flowers?', true);              --id: 4

-- Associating top-level threads with forums
INSERT INTO forums_threads (forum_id, thread_id)
VALUES
(1, 1), -- General Discussion
(1, 2), -- General Discussion
(2, 3), -- Help/Assistance
(2, 4); -- Help/Assistance

-- Creating replies to the top-level threads
INSERT INTO threads (user_id, date, message, is_post)
VALUES
(4, NOW(), 'I recommend starting with succulents!', false),     --id: 5
(6, NOW(), 'Don''t water in the midday sun!', false),           --id: 6
(7, NOW(), 'I love using compost in my garden.', false),        --id: 7
(8, NOW(), 'Tulips and daffodils are my favorites.', false);    --id: 8

-- Connecting the replies to the top-level threads
INSERT INTO thread_connections (child_thread, parent_thread)
VALUES
(5, 1),
(6, 2),
(7, 3),
(8, 4);

-- Additional top-level threads (posts)
INSERT INTO threads (user_id, date, title, message, is_post)
VALUES
(11, NOW(), 'Soil Nutrition Tips', 'Discuss your favorite soil nutrients!', true),  --id: 9
(12, NOW(), 'Indoor Plant Care', 'Share your indoor plant care routine!', true),    --id: 10
(14, NOW(), 'Fruit Gardening', 'How to grow fruit in your backyard?', true),        --id: 11
(9, NOW(), 'Herb Gardening', 'Share tips on growing herbs in pots!', true),         --id: 12
(15, NOW(), 'Landscaping Ideas', 'Looking for unique landscaping ideas.', true),    --id: 13
(2, NOW(), 'Pest Control', 'How do you control pests organically?', true);          --id: 14

-- Associating additional top-level threads with forums
INSERT INTO forums_threads (forum_id, thread_id)
VALUES
(1, 9), -- General Discussion
(1, 10), -- General Discussion
(2, 11), -- Help/Assistance
(2, 12), -- Help/Assistance
(1, 13), -- General Discussion
(2, 14); -- Help/Assistance

-- Additional replies to the top-level threads
INSERT INTO threads (user_id, date, message, is_post)
VALUES
(1, NOW(), 'I use organic compost!', false),                    --id: 15
(3, NOW(), 'Spider plants are great for indoors.', false),      --id: 16
(4, NOW(), 'Tomatoes need plenty of sunlight!', false),         --id: 17
(7, NOW(), 'Basil and mint grow well together.', false),        --id: 18
(5, NOW(), 'Use native plants for landscaping.', false),        --id: 19
(6, NOW(), 'Neem oil works well against pests.', false);        --id: 20

-- Connecting additional replies to the top-level threads
INSERT INTO thread_connections (child_thread, parent_thread)
VALUES
(15, 9),
(16, 10),
(17, 11),
(18, 12),
(19, 13),
(20, 14);

-- Additional replies to the first level replies
INSERT INTO threads (user_id, date, message, is_post)
VALUES
(2, NOW(), 'Absolutely! Succulents are low-maintenance.', false),       --id: 21
(4, NOW(), 'I agree, watering early morning is best.', false),          --id: 22
(5, NOW(), 'Composting is the way to go!', false),                      --id: 23
(3, NOW(), 'I love tulips too!', false),                                --id: 24
(8, NOW(), 'I''ve heard of using coffee grounds for soil.', false),     --id: 25
(12, NOW(), 'I''m trying bonsai with indoor plants.', false),           --id: 26
(15, NOW(), 'I''m growing strawberries, any tips?', false),             --id: 27
(11, NOW(), 'I''m using rosemary with my herbs.', false),               --id: 28
(14, NOW(), 'Rock gardens are an interesting idea!', false),            --id: 29
(13, NOW(), 'I use garlic spray against aphids.', false),               --id: 30
(18, NOW(), 'And I love daffodils!', false);                            --id: 31

-- Connecting additional replies to the first level replies
INSERT INTO thread_connections (child_thread, parent_thread)
VALUES
(21, 5),
(22, 6),
(23, 7),
(24, 8),
(25, 9),
(26, 10),
(27, 11),
(28, 12),
(29, 13),
(30, 14),
(31, 8);

INSERT INTO virtual_gardens (user_id, plant_id)
VALUES
(1, 8338),
(1, 645),
(1, 9172),
(1, 6218),
(1, 6931),
(2, 6608),
(2, 438),
(2, 3678),
(2, 5419),
(2, 5374),
(2, 9635),
(6, 6776),
(6, 8810),
(6, 7054),
(7, 1736),
(7, 8259),
(8, 8587),
(8, 8289),
(8, 2215),
(9, 7810),
(9, 6056),
(9, 1116),
(9, 9029),
(10, 2671),
(10, 1792),
(10, 1643),
(10, 6409),
(11, 6777),
(11, 2228),
(11, 6696),
(11, 8006),
(11, 197),
(11, 5056),
(12, 4119),
(12, 3714),
(12, 3865),
(12, 2292),
(12, 2312),
(13, 3177),
(13, 4816),
(13, 5013),
(14, 3353),
(14, 3767),
(14, 2943),
(14, 1290),
(14, 4280),
(14, 173),
(15, 2836),
(15, 8535),
(15, 7095),
(15, 3038),
(15, 3521),
(15, 8630),
(16, 9061),
(16, 671),
(16, 4507),
(16, 5192),
(16, 6631),
(16, 1107),
(17, 5137),
(17, 5267),
(17, 3813),
(17, 8944),
(17, 8724),
(18, 2088),
(18, 4927),
(18, 2402),
(18, 2950),
(18, 8957),
(19, 7975),
(19, 1407),
(19, 9823),
(19, 8177);

COMMIT TRANSACTION;
