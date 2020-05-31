drop table credential;

drop table song_list;

create table credentials (id int not null auto_increment, username varchar(6) not null, password varchar(6) not null, affiliation int, primary key (id));

create table song_list (id int not null auto_increment, song_name varchar(50) not null, musician varchar(50), year int, album varchar (50), genre varchar(15), primary key (id));

insert into credentials set username = 'hello', password = 'there', affiliation = 1;

insert into song_list set song_name = 'Don''t Stop Believin''', musician = 'Journey', year = 1981, album = 'Escape', genre = 'Rock';

update song_list set song_name = case when 1 then 'Faithfully' else song_name end where id = 10