DROP TABLE IF EXISTS settings;
CREATE TABLE settings (
  id serial PRIMARY KEY,
  setting_key VARCHAR(50),
  value VARCHAR(50)
);
