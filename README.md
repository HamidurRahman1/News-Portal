## Spring Boot REST API with Spring Security

## What this project is about:
This project is about building mini news portal. Only subscribed user can read, comment on articles. 
The public can only see part of an article. A user with <b>Publisher</b> role can insert or delete an article. 
A user with <b>Editor</b> can only update an article. A subscribed user will have a <b>User</b> role, can delete a comment and an <b>Admin</b> can do anything
mentioned as well as add an author and assign roles to users. Every action is performed by checking and verifying if one has 
the correct permissions (roles) to do so.


## Purpose of this project:
This is a demo project for learning and practicing purposes only as well as demonstrating the knowledge of the below technologies.
* Full-Stack Development  
* Spring Boot
* Spring Security
* Spring AOP
* AWS Elastic BeanStalk
* H2/AWS RDS
* Restricted endpoints
* Exceptions handling
* Custom Error Response
* HTML
* JavaScript  

## Environments:

| Props|Development |Production|
| :---:  | :---: |  :---: |
|Database| H2   | MySQL
|Host (DB)  |Local (in memory) | AWS
|Host (app)| Local | AWS Elastic Beanstalk 

<br>

## API Documentation:
* All paths are relative to <strong>domain/blogs/api/v1/...</strong> in production and development
* At this moment inbound and outbound data supported only in JSON format

<br>

##### Public API:
* All paths are relative to <strong>domain/blogs/api/v1/public/...</strong> in production and development

| HTTP METHOD|Path | Explanation|
| :---:  | :---: | :---: |
|GET| /articles| returns all articles and associated authors
|GET| /comments| returns all comments
|GET| /articles/no-author | returns all articles that do not have any authors
|GET| /article/{articleId}/comments | returns all comments associated with specified {articleId}  
  
<br>  

#### Roles:
This is for simplification  

|ID| Role | Indicator 
| :---: | :---: | :---: |
|1| ADMIN | A
|2| PUBLISHER | P
|3| EDITOR | E
|4| USER | U

<br>

##### Protected API:
* All paths are relative to <strong>domain/blogs/api/v1/r/...</strong> in production and development

| HTTP METHOD|Path | Accessible with Role(s) | Explanation|
| :---:  | :---: | :---: | :---: |
|GET| /authors | A, P, E, U | returns all authors
|GET| /author/{authorId} |  A, P, E, U | returns an author associated with specified <b>{authorId}</b>
|GET| /author/{authorId}/articles |  A, P, E, U | returns all articles associated with specified <b>{authorId}</b>
|GET| /comment/{commentId} |  A, P, E, U | returns a comment associated with specified <b>{commentId}</b>
|POST| /insert/author | A | inserts a new author, <b>{loginId}</b> must be specified which was generated from <b>/insert/user</b>
|POST| /insert/article |  A, P | inserts a new article, at least one {authorId} must be associated with this article
|POST| /insert/comment/article |  A, U | inserts a new comment to the specified article
|POST| /insert/user |  A | inserts a new user
|POST| /insert/user/{userId}/role/{roleId} |  A | adds a role specified by <b>{roleId}</b> to a specified user with specified by <b>{userId}</b>
|PUT| /update/author |  A | updates an existing author's attributes, authorId must be present
|PUT| /update/article |  A, E | updates an existing article's attributes, articleId must be present 
|PUT| /update/comment |  A, U | updates an existing comment, commentId must be present
|DELETE| /delete/author/{authorId} |  A | deletes an author specified by <b>{authorId}</b>
|DELETE| /delete/comment/{commentId} |  A, U | deletes a comment specified by <b>{commentId}</b> 
|DELETE| /delete/article/{articleId} |  A, P | deletes an article specified by <b>{articleId}</b>
|DELETE| /delete/user/{userId}/role/{roleId} |  A | revoke a role specified by <b>{roleId}</b> from a user specified by <b>{userId}</b> 
|DELETE| /delete/user/{userId} |  A | deletes a user specified by <b>{userId}</b>
<br>