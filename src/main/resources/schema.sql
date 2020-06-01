
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