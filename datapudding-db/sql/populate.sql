INSERT INTO dp.article (article_id, ts, body)
VALUES ('73d03054-7053-4cd2-9013-4c507a52ff77', '2004-10-19 10:23:54', 'This is an interesting article');

INSERT INTO dp.comment (comment_id, article_id, ts, author, body)
VALUES ('d05505ad-a85f-4673-9c5a-ba37bced1a55', '73d03054-7053-4cd2-9013-4c507a52ff77', '2004-10-19 11:23:54', 'James', 'This is an insightful comment');
