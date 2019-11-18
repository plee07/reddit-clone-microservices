# reddit-clone-microservices
## Design
![design](images/design.png)

**Api-Gateway**
- API Gateway will act as the Authentication service. Once the validity of the token is confirmed, a pre-filter is applied to send to all route the username and userId in the request header.

**Inter-service Communication**
- We used RestTemplate for all interservice communication
- Post to Comment: Delete Post will call Comment microservice to delete all comments with deleted postId
- Comment to Post: Adding comment will first send a request to Post microservice with postId to confirm the existence of the postId. Comment will send either a HttpStatus.FOUND or HttpStatus.NOT_FOUND. If postId is found, the comment is created, else don't create and return null.


## Pivotal Tracker
- https://www.pivotaltracker.com/n/projects/2416905

# Technologies Used
<li>
  Intellij
<li>
  Docker
<li>  
  Spring-boot
 <li> 
  Postman
