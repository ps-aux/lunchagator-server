DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;

DROP TABLE IF EXISTS menu_item;
DROP TABLE IF EXISTS daily_menu;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS menu_provider_info;

CREATE TABLE menu_provider_info (
  id          INTEGER PRIMARY KEY,
  type        VARCHAR(64) NOT NULL,
  zomato_id   INTEGER UNIQUE,
  strategy_id VARCHAR(256)
);

CREATE TABLE restaurant (
  id                    INTEGER PRIMARY KEY,
  name                  VARCHAR(256) NOT NULL,
  address               VARCHAR(256) NOT NULL,
  url                   VARCHAR(256) NOT NULL,
  latitude              DECIMAL      NOT NULL,
  longitude             DECIMAL      NOT NULL,
  menu_provider_info_id INTEGER REFERENCES menu_provider_info (id)
);

CREATE TABLE daily_menu (
  id            INTEGER PRIMARY KEY,
  day           DATE                               NOT NULL,
  restaurant_id INTEGER REFERENCES restaurant (id) NOT NULL
);

CREATE TABLE menu_item (
  id            INTEGER PRIMARY KEY,
  name          VARCHAR(1024)                       NOT NULL,
  price         DECIMAL                            NOT NULL,
  daily_menu_id INTEGER REFERENCES daily_menu (id) NOT NULL
);



