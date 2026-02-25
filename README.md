# Getting Started - Rate Limit Service

## About
A scalable **Rate Limit Service** built using the **Token Bucket Alorithm** with in memory caching.

This service provides per-user rate limiting for Spring Boot Application and is designed for high-performance API systems.

### Features
* Token Bucket Algorithm
* Per-user rate limiting
* Thread-safe implementation at user bucket level
* Caffeine cache with expiry
* Pluggable for redis or any other cache

### Architecture

Client --> Controller --> Handler --> Cache --> User Bucket

### How to Run locally with docker?

Follow these steps to run the service locally in docker

1) mvn clean install -DskipTests
2) docker build -t ratelimitservice .
3) docker run -d -p 8080:8080 --name ratelimitservice-container ratelimitservice

To verify if it is running, run following command on terminal:
curl --location 'localhost:8080/health'

Should return: RUNNING

### How to Test?
1) curl --location 'localhost:8080/limit/api/v1/allow/test-client-01'

Should return 200 response code:
{
"allowed": false,
"error": "Client not found or not registered. Please register first."
}

2) curl --location 'localhost:8080/limit/api/v1/client' \
   --header 'Content-Type: application/json' \
   --data '{
   "clientId" : "test-client-01",
   "limitPerMinute" : 5
   }'

Should return 200 response code

3) Run again 
curl --location 'localhost:8080/limit/api/v1/allow/test-client-01'

Should return 200 response code with body:
{
"allowed": true
}

4) Run step 3 again four more times and it should return same result.
5) On 6th hit the response will change to:
   {
   "allowed": false,
   "error": "Rate limit exceeded. Please try after some time."
   }
6) Wait for 1 min then it should again allow the requests