insert into category(id, name) values (1, 'java');
insert into category(id, name) values (2, 'spring');
insert into category(id, name) values (3, 'sql');

insert into board(id, name, title, content, read_count, create_date, category_id)
values(1, 'kim', 'hello title 1', ' content 1', 0, now(),1 );

insert into board(id, name, title, content, read_count, create_date, category_id)
values(2, 'lee', 'hello title 2', ' content 2', 0, now(), 2 );

insert into board(id, name, title, content, read_count, create_date, category_id)
values(3, 'kang', 'hello title 3', ' content 3', 0, now(), 3);


insert into member_role (id , name) values (1, 'USER');
insert into member_role (id , name) values (2, 'ADMIN');

insert into member(id, name, email, password, regdate)
values(1, 'kim', 'urstory@gmail.com', '{bcrypt}$2a$10$G5E3.paSJfHL1iG/Ccy.meLL2eu4I1S22nqqpShxYYU1QnNFzkiP2', now());

insert into member(id, name, email, password, regdate)
values(2, 'kang', 'carami@gmail.com', '{bcrypt}$2a$10$G5E3.paSJfHL1iG/Ccy.meLL2eu4I1S22nqqpShxYYU1QnNFzkiP2', now());

insert into member_member_role(member_id, member_role_id)
values(1, 1);

insert into member_member_role(member_id, member_role_id)
values(1, 2);

insert into member_member_role(member_id, member_role_id)
values(2, 1);


