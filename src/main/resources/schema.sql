create table users
(
  user_id int primary key auto_increment,
  first_name varchar(50) not null,
  last_name varchar(50) not null,
  username varchar(60) unique not null,
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
  primary key(user_id, role_id),
  foreign key (user_id) references users(user_id),
  foreign key (role_id) references roles(role_id)
);

create table articles
(
  article_id int primary key auto_increment,
  title varchar(1000) not null,
  body varchar(10000) not null,
  timestamp varchar (50) not null,
  is_published tinyint not null
);

create table authors_articles
(
  author_id int not null,
  article_id int not null,
  primary key(author_id, article_id),
  foreign key (author_id) references users(user_id),
  foreign key (article_id) references articles(article_id)
);

create table comments
(
  comment_id int primary key auto_increment,
  comment varchar(250) not null,
  article_id int not null,
  foreign key (article_id) references articles(article_id)
);