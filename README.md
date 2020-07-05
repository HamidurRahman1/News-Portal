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
|POST| /user/signup | inserts a new user
<br>  

#### Roles:
This is for simplification  

|ID| Role | Indicator 
| :---: | :---: | :---: |
|1| ADMIN | AD
|2| PUBLISHER | P
|3| EDITOR | E
|4| AUTHOR | AU
|4| USER | U

<b>***</b> Every account once gets created get the default role which is USER. User role gives one <b>least privilege</b>. Every other role(s) is derived from USER.
<br>

##### Protected API:
* All paths are relative to <strong>domain/blogs/api/v1/r/...</strong> in production and development

| HTTP METHOD| Path | Accessible with Role(s) | Explanation|
| :---:  | :---: | :---: | :---: |
|GET| /authors | U | returns all authors
|GET| /author/{authorId} |  U | returns an author associated with specified <b>{authorId}</b>
|GET| /author/{authorId}/articles |  U | returns all articles associated with specified <b>{authorId}</b>
|GET| /comment/{commentId} |  U | returns a comment associated with specified <b>{commentId}</b>
|POST| /insert/article |  AD, P | inserts a new article, at least one {authorId} must be associated with this article
|POST| /insert/comment/article |  U | inserts a new comment to the specified article
|POST| /insert/user/{userId}/role/{roleId} |  AD | adds a role specified by <b>{roleId}</b> to a specified user with specified by <b>{userId}</b>
|PUT| /update/user |  U | updates an existing user's attributes, userId must be present
|PUT| /update/article |  AD, E | updates an existing article's attributes, articleId must be present 
|PUT| /update/comment |  U | updates an existing comment, commentId must be present
|PATCH| /deactivate/user/{userId} |  U | deactivate/disable an existing account, userId must be specified
|DELETE| /delete/author/{authorId} |  AD | Detaches everything related to this Author along with <b>AUTHOR</b> role. Only UserDetails (personal info and login) are kept active with <b>USER</b> role.
|DELETE| /delete/comment/{commentId} |  U | deletes a comment specified by <b>{commentId}</b> 
|DELETE| /delete/published/article/{articleId} |  AD, P | deletes an already published article specified by <b>{articleId}</b>
|DELETE| /delete/unpublished/article/{articleId} |  AD, P | deletes a non-published article specified by <b>{articleId}</b>
|DELETE| /delete/user/{userId}/role/{roleId} |  AD | revoke a role specified by <b>{roleId}</b> from a user specified by <b>{userId}</b> 
|DELETE| /delete/user/{userId} |  AD | deletes a user specified by <b>{userId}</b>
<br>