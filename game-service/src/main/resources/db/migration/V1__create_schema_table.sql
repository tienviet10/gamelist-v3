CREATE TABLE IF NOT EXISTS games (
  avg_score FLOAT,
  bannerURL VARCHAR(255),
  description VARCHAR(2000),
  imageURL VARCHAR(255),
  name VARCHAR(255),
  releaseDate TIMESTAMP,
  total_rating INTEGER,
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS genres (
  id BIGINT NOT NULL,
  created_at TIMESTAMP,
  name VARCHAR(255) UNIQUE,
  updated_at TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS platforms (
  id BIGINT NOT NULL,
  created_at TIMESTAMP,
  name VARCHAR(255) UNIQUE,
  updated_at TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tags (
  id BIGINT NOT NULL,
  created_at TIMESTAMP,
  name VARCHAR(255) UNIQUE,
  updated_at TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS posts (
  text VARCHAR(255),
  post_id BIGINT NOT NULL,
  user_id VARCHAR(128),
  PRIMARY KEY (post_id)
);

CREATE TABLE IF NOT EXISTS games_genres (
  game_id BIGINT NOT NULL,
  genre_id BIGINT NOT NULL,
  PRIMARY KEY (game_id, genre_id)
);

CREATE TABLE IF NOT EXISTS games_platforms (
  game_id BIGINT NOT NULL,
  platform_id BIGINT NOT NULL,
  PRIMARY KEY (game_id, platform_id)
);

CREATE TABLE IF NOT EXISTS games_tags (
  game_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  PRIMARY KEY (game_id, tag_id)
);

CREATE TABLE IF NOT EXISTS interactive_entities (
  id BIGINT NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS like_entities (
  id BIGINT NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  interactive_entity_id BIGINT NOT NULL,
  user_id VARCHAR(128),
  PRIMARY KEY (id),
  UNIQUE (user_id, interactive_entity_id),
  FOREIGN KEY (interactive_entity_id) REFERENCES interactive_entities
);

CREATE TABLE IF NOT EXISTS status_updates (
  game_status VARCHAR(255) CHECK (
    game_status IN (
      'Playing',
      'Completed',
      'Paused',
      'Planning',
      'Dropped',
      'Inactive',
      'JustAdded'
    )
  ),
  status_update_id BIGINT NOT NULL,
  user_game_id BIGINT,
  PRIMARY KEY (status_update_id)
);

CREATE TABLE IF NOT EXISTS user_followers (
  follower_id BIGINT NOT NULL,
  following_id BIGINT NOT NULL,
  PRIMARY KEY (follower_id, following_id)
);

CREATE TABLE IF NOT EXISTS user_games (
  id BIGINT NOT NULL,
  completed_date TIMESTAMP,
  created_at TIMESTAMP,
  game_note VARCHAR(255),
  game_status VARCHAR(255) CHECK (
    game_status IN (
      'Playing',
      'Completed',
      'Paused',
      'Planning',
      'Dropped',
      'Inactive',
      'JustAdded'
    )
  ),
  private BOOLEAN,
  rating INTEGER,
  start_date TIMESTAMP,
  updated_at TIMESTAMP,
  game_id BIGINT NOT NULL,
  user_id VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id VARCHAR(128) NOT NULL,
  roles VARCHAR(255) CHECK (roles IN ('ROLE_USER', 'ROLE_ADMIN'))
);

CREATE TABLE IF NOT EXISTS comments (
  text VARCHAR(255),
  comment_id BIGINT NOT NULL,
  interactive_entity_id BIGINT,
  user_id VARCHAR(128),
  PRIMARY KEY (comment_id),
  FOREIGN KEY (interactive_entity_id) REFERENCES interactive_entities
);

CREATE TABLE IF NOT EXISTS game_journals (
  content VARCHAR(255),
  game_journal_id BIGINT NOT NULL,
  user_id VARCHAR(128),
  PRIMARY KEY (game_journal_id)
);

-- Add indexes
CREATE INDEX IF NOT EXISTS nameIndex ON games (name);

CREATE INDEX IF NOT EXISTS releaseDateIndex ON games (releaseDate);

CREATE INDEX IF NOT EXISTS avgScoreIndex ON games (avg_score);

CREATE INDEX IF NOT EXISTS totalRatingIndex ON games (total_rating);

CREATE INDEX IF NOT EXISTS mulitIndexNameIdAsc ON games (name ASC, id);

CREATE INDEX IF NOT EXISTS mulitIndexNameIdDesc ON games (name DESC, id);

CREATE INDEX IF NOT EXISTS mulitIndexReleaseDateIdAsc ON games (releaseDate ASC, id);

CREATE INDEX IF NOT EXISTS mulitIndexReleaseDateIdDesc ON games (releaseDate DESC, id);

CREATE INDEX IF NOT EXISTS mulitIndexAvgScoreIdAsc ON games (avg_score ASC, id);

CREATE INDEX IF NOT EXISTS mulitIndexAvgScoreIdDesc ON games (avg_score DESC, id);

CREATE INDEX IF NOT EXISTS mulitIndexTotalRatingIdAsc ON games (total_rating ASC, id);

CREATE INDEX IF NOT EXISTS mulitIndexTotalRatingIdDesc ON games (total_rating DESC, id);

CREATE INDEX IF NOT EXISTS game_genres_gameId ON games_genres (game_id);

CREATE INDEX IF NOT EXISTS game_genres_genreId ON games_genres (genre_id);

CREATE INDEX IF NOT EXISTS game_platforms_gameId ON games_platforms (game_id);

CREATE INDEX IF NOT EXISTS game_platforms_platformId ON games_platforms (platform_id);

CREATE INDEX IF NOT EXISTS game_tags_gameId ON games_tags (game_id);

CREATE INDEX IF NOT EXISTS game_tags_tagId ON games_tags (tag_id);