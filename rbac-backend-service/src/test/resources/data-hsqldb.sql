insert into user(name,password,create_time,update_time) values ('admin', '3D66051F38F97F59d9a6cf2d699cb7114044ef5c7ac069e70114cdd21e0b50ee443cbe4e59169553', '2015-03-13 00:00:00', '2015-03-13 00:00:00');
insert into user(name,password,create_time,update_time) values ('test', '14754AD68072330Fc7591de38b820ca602378668e2af045365362a08d80ffb849fdba7fcc29a10bf', '2015-03-13 00:00:00', '2015-03-13 00:00:00');
insert into role(name) values ('ROLE_ADMIN');
insert into user_role values((select id from user where name='admin'),(select id from role where name='ROLE_ADMIN'));
insert into role(name) values ('ROLE_PLAIN_USER');
insert into user_role values((select id from user where name='test'),(select id from role where name='ROLE_PLAIN_USER'));

insert into resource(name) values ('roles');
insert into role_permission(role_id,resource_id,permission) values((select id from role where name='ROLE_ADMIN'),(select id from resource where name='roles'),255);
insert into resource(name) values ('users');
insert into role_permission(role_id,resource_id,permission) values((select id from role where name='ROLE_ADMIN'),(select id from resource where name='users'),255);
insert into resource(name) values ('resources');
insert into role_permission(role_id,resource_id,permission) values((select id from role where name='ROLE_ADMIN'),(select id from resource where name='resources'),255);

