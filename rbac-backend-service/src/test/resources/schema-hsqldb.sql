
SET DATABASE SQL SYNTAX MYS TRUE;

CREATE TABLE user (
id  INTEGER NOT NULL AUTO_INCREMENT ,
name  varchar(255) NOT NULL ,
password varchar(255),
create_time DATETIME NOT NULL,
update_time DATETIME,
deleted boolean DEFAULT 0,
PRIMARY KEY (id)
);
CREATE UNIQUE INDEX USER_IDX_NAME ON user(name);

CREATE TABLE role (
id  integer NOT NULL AUTO_INCREMENT ,
name  varchar(255) NOT NULL ,
PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ROLE_IDX_NAME ON role(name);

CREATE TABLE user_role (
user_id  integer NOT NULL,
role_id  integer NOT NULL,
PRIMARY KEY (user_id,role_id),
FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE resource (
id  integer NOT NULL AUTO_INCREMENT ,
name  varchar(255) NOT NULL ,
PRIMARY KEY (id)
);
CREATE UNIQUE INDEX RESOURCE_IDX_NAME ON resource(name);

CREATE TABLE role_permission (
role_id  integer NOT NULL,
resource_id  integer NOT NULL,
permission  integer NOT NULL,
PRIMARY KEY (role_id,resource_id),
FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE,
FOREIGN KEY (resource_id) REFERENCES resource (id) ON DELETE CASCADE
);