CREATE DATABASE IF NOT EXISTS mandal
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use mandal;

CREATE TABLE IF NOT EXISTS user(
id VARCHAR(128) PRIMARY KEY, -- "email"
jsonstr VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS community(
no INT UNSIGNED PRIMARY KEY, 
id VARCHAR(128),

jsonstr VARCHAR(8192)
);

CREATE TABLE IF NOT EXISTS friend( 
id VARCHAR(128), -- "email"
frid VARCHAR(128), -- "email“ 
INDEX idx1(id)
);


use mandal;