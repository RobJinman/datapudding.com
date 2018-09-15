CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA dp;

CREATE TABLE IF NOT EXISTS dp.article (
  article_id UUID PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  body TEXT
);

CREATE TABLE IF NOT EXISTS dp.comment (
  comment_id UUID PRIMARY KEY,
  article_id UUID REFERENCES dp.article(article_id) NOT NULL,
  ts TIMESTAMP NOT NULL,
  author TEXT NOT NULL,
  body TEXT NOT NULL
);
