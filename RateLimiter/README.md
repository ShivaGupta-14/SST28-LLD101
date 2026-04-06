# Pluggable Rate Limiting System - Low Level Design

## Problem
Rate limiting system for external resource calls. Not every API request consumes quota, only the ones that actually hit the paid external service.

## Class Diagram

```
+------------------+
|      Main        |
+------------------+
| + main(args)     |
+------------------+
        |
        | creates
        v
+--------------------+       +-------------------+
| InternalService    |------>| ExternalService   |
+--------------------+       +-------------------+
| - rateLimiter      |       | + call(data)      |
| -  externalService  |       +-------------------+
+--------------------+
| + handleRequest()  |
| + setRateLimiter() |
+--------------------+
        |
        | uses
        v
+---------------------+
| RateLimiter (intf)  |
+---------------------+
| + allowRequest(key) |
+---------------------+
        ^           ^
        |           |
+---------------+  +--------------------+
|FixedWindow    |  |SlidingWindow       |
|Counter        |  |Counter             |
+---------------+  +--------------------+
| - maxRequests |  | - maxRequests      |
| - windowSizeMs|  | - windowSizeMs     |
| - counts      |  | - counts           |
| - windowStart |  +--------------------+
+---------------+  | + allowRequest()   |
| + allowRequest()  +--------------------+
+---------------+

Relationships:
  InternalService ---has one---> RateLimiter
  InternalService ---has one---> ExternalService
  FixedWindowCounter implements RateLimiter
  SlidingWindowCounter implements RateLimiter
```

## Design Decisions

- RateLimiter is an interface with a single method allowRequest(key). Any new algorithm just needs to implement this one method and it plugs right in. InternalService holds a reference to the interface so we can swap algorithms at runtime using setRateLimiter().
- Rate limiting happens only when the service actually needs to call the external resource. If business logic doesn't need the external call, the rate limiter is never consulted. This is handled by the needsExternalCall flag in handleRequest().
- The key parameter in allowRequest is flexible. Callers can pass customer ID, tenant ID, API key, or provider name. The rate limiter doesn't care what the key represents, it just tracks counts per key.
- Thread safety is handled with synchronized on the allowRequest method. Simple and works for single JVM. For distributed systems you would need something like Redis but that is out of scope.
- Fixed Window uses two maps: one for counts and one for tracking when the window started. When the window expires, both reset.
- Sliding Window uses aligned time buckets. It calculates a weighted sum of the previous window count and current window count. The weight is based on how much of the previous window overlaps with the sliding window.

## Trade-offs: Fixed Window vs Sliding Window

Fixed Window Counter:
- Simpler to implement, less memory (only current window per key)
- Problem: burst at window boundary. If the limit is 5/min, a client can make 5 calls at 0:59 and 5 more at 1:01, getting 10 calls in 2 seconds
- Good enough when small bursts at boundaries are acceptable

Sliding Window Counter:
- Smooths out the boundary problem by weighting the previous window
- Slightly more complex, stores counts for current and previous windows
- Not perfectly accurate since it estimates previous window distribution as uniform
- Better choice when you want more even rate limiting

## How to Run
```bash
cd src
javac *.java
java Main
```
