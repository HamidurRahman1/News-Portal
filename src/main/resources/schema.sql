
create table users
(
  user_id int primary key auto_increment,
  username varchar(50) unique not null,
  password varchar(70) not null,
  enabled tinyint not null
);

create table roles
(
  role_id int primary key auto_increment,
  role varchar(15) unique not null
);

create table users_roles
(
  user_id int not null,
  role_id int not null,

  foreign key (user_id) references users(user_id),
  foreign key (role_id) references roles(role_id)
);

create table authors
(
  author_id int primary key auto_increment,
  author_first_name varchar(50) not null,
  author_last_name varchar(50) null
);

create table articles
(
  article_id int primary key auto_increment,
  article_title varchar(1000) not null,
  article_body varchar(10000) not null,
  article_publish_date date not null
);

create table authors_articles
(
  author_id int not null,
  article_id int not null,

  foreign key (author_id) references authors(author_id),
  foreign key (article_id) references articles(article_id)
);

create table comments
(
  comment_id int primary key auto_increment,
  comment varchar(250) not null,
  article_id int not null,

  foreign key (article_id) references articles(article_id)
);