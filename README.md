## Spring Boot REST API with Spring Security

## Purpose of this project
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

## Environments

| Props|Development |Production|
| :---:  | :---: |  :---: |
|Database| H2   | MySQL
|Hosted(DB)  |Local (in memory) | AWS  

## API Documentation

##### Public API
* All path is relative to <strong>domain/blogs/api/v1/public</strong> in production
* At this moment inbound and outbound data supported only in JSON format

| HTTP METHOD|Path | Explanation|Example|
| :---:  | :---: | :---: |  :---: |
|Get| /articles| returns all articles and associated authors | .
|Get| /comments| returns all comments | .
|Get| /articles/no-author | returns all articles that do not have any authors | .
|Get| /article/{articleId}/comments | returns all comments associated with specified {articleId}  | .
