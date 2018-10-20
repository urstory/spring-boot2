insert into category(id, name) values (1, 'java');
insert into category(id, name) values (2, 'spring');
insert into category(id, name) values (3, 'sql');

insert into board(id, name, title, content, read_count, create_date, category_id)
values(1, 'kim', 'hello title 1', ' content 1', 0, now(),1 );

insert into board(id, name, title, content, read_count, create_date, category_id)
values(2, 'lee', 'hello title 2', ' content 2', 0, now(), 1 );

insert into board(id, name, title, content, read_count, create_date, category_id)
values(3, 'kang', 'hello title 3', ' content 3', 0, now(), 1);