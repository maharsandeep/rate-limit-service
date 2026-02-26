# 🚀 Rate Limit Service

A scalable and high-performance **Rate Limit Service** built using the
**Token Bucket Algorithm** with in-memory caching.

This service provides per-user rate limiting for Spring Boot
applications and is designed to handle **high concurrency**, support
**horizontal scaling**, and maintain **efficient memory usage** across
different deployment environments.

------------------------------------------------------------------------

## 📌 Key Features

-   🔢 **Token Bucket Algorithm** (burst-aware rate limiting)
-   🧠 **Concurrency-safe implementation** (thread-safe bucket updates)
-   💾 **Efficient Memory Management** using Caffeine cache
-   🔌 **Pluggable Cache Layer** (in-memory by default, Redis-supported
    for distributed systems)
-   📈 **Horizontally Scalable Architecture**
-   🐳 **Docker Containerized** and deployment-ready

------------------------------------------------------------------------

## 🏗 Architecture

### High-Level Flow

``` mermaid
flowchart LR
    A[Client] --> B[Controller]
    B --> C[Handler]
    C --> D[Cache Layer]
    D --> E[User Bucket]
```

------------------------------------------------------------------------

### Component Description

### 1️⃣ Client

External system or user making HTTP API requests to the rate limit
service.

### 2️⃣ Controller

-   Entry point for REST APIs\
-   Validates requests\
-   Routes traffic to the handler layer

### 3️⃣ Handler

-   Implements the core rate-limiting logic\
-   Applies Token Bucket algorithm\
-   Ensures thread-safe execution for concurrent requests

### 4️⃣ Cache Layer

-   Stores client buckets\
-   Default: In-memory (Caffeine)\
-   Optional: Redis or other distributed cache provider

### 5️⃣ User Bucket

-   Maintains token state per client\
-   Tracks refill rate and consumption\
-   Determines whether a request is allowed or rejected

------------------------------------------------------------------------

### 🔄 Request Lifecycle

1.  Client sends request\
2.  Controller receives and forwards to handler\
3.  Handler checks cache for client bucket\
4.  Bucket tokens are consumed if available\
5.  Response returned (Allowed / Rate Limit Exceeded)

------------------------------------------------------------------------

### 📈 Scalability Notes
The caching layer can operate in:
-   **In-Memory Mode (Default)** supports single-instance deployments
-   **Distributed Cache Mode**  enables horizontal scaling across multiple
    service instances
-   Designed for high concurrency with efficient memory management


------------------------------------------------------------------------

## 📦 Technologies Used

| Technology      | Purpose                     | Version |
|---------------|----------------------------|----------|
| Java          | Programming Language        | 17 |
| Spring Boot   | Application Framework       | 4.0.3 |
| Caffeine      | In-memory Caching           | Spring Boot managed version |
| Maven         | Build Tool                  | — |
| Docker        | Containerization            | — |
| Redis (Optional) | Distributed Cache Backend | Latest compatible |

------------------------------------------------------------------------

## 🧠 Concurrency & Performance

### ✅ Concurrency Handling

-   Thread-safe bucket access to prevent race conditions
-   Safe handling of concurrent requests for the same client
-   Designed for high-throughput API environments

### 💾 Efficient Memory Usage

-   Uses Caffeine in-memory cache for ultra-fast lookups
-   Automatic eviction and expiration
-   Minimal memory footprint for large client sets

### 📈 Scalability

| Mode | Description |
|------|------------|
| Single Node | Runs fully in-memory with zero external dependencies |
| Distributed | Plug in Redis (or any cache provider) to share rate-limit state across multiple service instances |

------------------------------------------------------------------------

## 🐳 Run Locally (Docker)

### 1️⃣ Build the Project

```bash
# Build the project (skip tests if needed)
mvn clean install -DskipTests
```

### 2️⃣ Build Docker Image

```bash
docker build -t ratelimitservice .`
```
### 3️⃣ Run Container

```bash
docker run -d -p 8080:8080 --name ratelimitservice-container
ratelimitservice`
```
### 4️⃣ Verify Health

`curl --location 'localhost:8080/health'`

Expected Output:

`RUNNING`

------------------------------------------------------------------------

## 🔍 How to Test

### 1️⃣ Access Before Registration
```bash
curl --location 'localhost:8080/limit/api/v1/allow/test-client-01'
```

Response:

`{ "allowed": false, "error": "Client not found or not registered. Please
register first." }`

------------------------------------------------------------------------

### 2️⃣ Register a Client
```bash
curl --location 'localhost:8080/limit/api/v1/client'\
--header 'Content-Type: application/json'\
--data '{ "clientId" : "test-client-01", "limitPerMinute" : 5 }'
```

**Note**: We have set 5 requests are allowed in 1 min

------------------------------------------------------------------------

### 3️⃣ Call Rate Limit Endpoint
```bash
curl --location 'localhost:8080/limit/api/v1/allow/test-client-01'
```

For first 5 requests within a minute, the response is:

`{ "allowed": true }`

------------------------------------------------------------------------

### 4️⃣ Sixth Request Within Same Minute

The response is:

`{ "allowed": false, "error": "Rate limit exceeded. Please try after some
time." }`

------------------------------------------------------------------------

### 5️⃣ After 1 Minute

Once the token bucket refills (after 1 minute), requests will be allowed
again for 5 times.

------------------------------------------------------------------------

## 🔁 Cache Modes

### 🧠 In-Memory Mode (Default)

-   Uses Caffeine cache
-   Ideal for single-instance deployment
-   Extremely low latency
-   Automatic cleanup & expiry

### 🌍 Distributed Mode (Redis or Any Cache Provider)

-   Shared rate-limit state across multiple service instances
-   Enables horizontal scaling
-   Suitable for Kubernetes or clustered deployments
-   Cache abstraction layer allows plugging in Redis easily

------------------------------------------------------------------------

## 🚢 Containerization & Deployment

* Fully containerized with Docker
* Ready for:
  * Kubernetes
  * Docker Swarm
  * ECS
  * Any container orchestration platform
* Works seamlessly with external Redis containers for distributed
    scaling

------------------------------------------------------------------------

## 🧪 Test Strategy

-   Manual testing via cURL commands (as shown above)
-   Spring Boot unit and integration tests
-   Concurrency testing by simulating parallel requests
-   Distributed cache testing by integrating Redis backend

------------------------------------------------------------------------

## 📘 Summary

This Rate Limit Service is:

-   ⚡ High-performance
-   🔒 Concurrency-safe
-   💾 Memory-efficient
-   📈 Horizontally scalable
-   🐳 Container-ready
-   🔌 Pluggable with Redis or other cache providers

------------------------------------------------------------------------

### ⭐ Ready for Production Deployment

Designed to run efficiently on a single machine or scale seamlessly
across distributed systems.
