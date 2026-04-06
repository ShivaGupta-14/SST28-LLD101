# Distributed Cache - Low Level Design

## Problem
In-memory distributed cache with pluggable distribution strategy and eviction policy. Supports get/put with automatic DB fallback on cache miss.

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
+---------------------+       +-------------------+
| DistributedCache    |------>| Database          |
+---------------------+       +-------------------+
| - nodes: List       |       | + get(key)        |
| - strategy          |       | + put(key, value) |
| - database          |       +-------------------+
+---------------------+
| + get(key)          |
| + put(key, value)   |
+---------------------+
      |           |
      v           v
+-----------+   +----------------------+
| CacheNode |   | DistributionStrategy |
+-----------+   |       (interface)     |
| - id      |   +----------------------+
| - capacity|   | + getNode(key, count)|
| - store   |   +----------------------+
| - eviction|            ^
+-----------+            |
| + get()   |   +------------------+
| + put()   |   | ModuloDistribution|
+-----------+   +------------------+
      |
      v
+---------------------+
| EvictionPolicy      |
|     (interface)     |
+---------------------+
| + keyAccessed(key)  |
| + evict()           |
| + remove(key)       |
+---------------------+
         ^
         |
+------------------+
| LRUEvictionPolicy|
+------------------+
| - order: LinkedList|
+------------------+

Relationships:
  DistributedCache ---has many---> CacheNode
  DistributedCache ---has one---> DistributionStrategy
  DistributedCache ---has one---> Database
  CacheNode ---has one---> EvictionPolicy
  ModuloDistribution implements DistributionStrategy
  LRUEvictionPolicy implements EvictionPolicy
```

## How Data is Distributed
DistributionStrategy decides which node stores a key. ModuloDistribution does `Math.abs(key.hashCode()) % nodeCount`. This spreads keys evenly across nodes. To switch to consistent hashing later, just create a new class implementing DistributionStrategy and pass it to DistributedCache.

## How Cache Miss is Handled
When get(key) is called, DistributedCache first checks the correct node (decided by strategy). If the key is not in that node, it fetches from the Database, stores the value in the node for future hits, and returns it.

## How Eviction Works
Each CacheNode has a capacity and an EvictionPolicy. When put() is called and the node is full, it asks the policy for which key to evict. LRUEvictionPolicy uses a LinkedList to track access order. The least recently accessed key (front of list) gets evicted. Every get or put moves the key to the end of the list.

## Extensibility
- New eviction policy: implement EvictionPolicy (e.g. LFU, MRU) and pass it when creating CacheNode
- New distribution strategy: implement DistributionStrategy (e.g. consistent hashing) and pass it to DistributedCache
- No existing code needs to change, just plug in the new implementation

## How to Run
```bash
cd src
javac *.java
java Main
```
