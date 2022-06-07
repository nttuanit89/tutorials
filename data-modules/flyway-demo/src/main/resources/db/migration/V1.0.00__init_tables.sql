CREATE SCHEMA IF NOT EXISTS user_profile;
CREATE TABLE IF NOT EXISTS user_profile.profile (
    id SERIAL PRIMARY KEY ,
    name varchar(255),
    price double precision
);