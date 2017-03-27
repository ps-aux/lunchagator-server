DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;

DROP TABLE IF EXISTS menu_info_source;
CREATE TABLE menu_info_source (
  id            INTEGER PRIMARY KEY,
  type          VARCHAR(64),
  restaurant_id INTEGER UNIQUE
);

DROP TABLE IF EXISTS restaurant;
CREATE TABLE restaurant (
  id                  INTEGER PRIMARY KEY,
  name                VARCHAR(256),
  address             VARCHAR(256),
  latitude            DECIMAL,
  longitude           DECIMAL,
  menu_info_source_id INTEGER REFERENCES menu_info_source (id)
);


