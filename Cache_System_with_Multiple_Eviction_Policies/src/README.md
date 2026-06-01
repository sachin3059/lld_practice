# Cache System with Multiple Eviction Policies
### LLD Round Preparation — SDE-1 / SDE-2 Level

---

## Problem Statement

Design and implement a cache system that supports multiple eviction policies.

Your cache should be able to store key-value pairs and dynamically support eviction policies like:
- **Least Recently Used (LRU)**
- **Least Frequently Used (LFU)**
- **FIFO**

Additionally, implement a flexible structure that allows adding new eviction policies easily.

---

## Requirements

- Implement a cache with configurable eviction policies (LRU, LFU, FIFO)
- Support standard cache operations:
    - `put(key, value)` — inserts or updates a key-value pair
    - `get(key)` — retrieves the value of a given key (returns `-1` if not found)
- Implement a mechanism to **switch eviction policies dynamically**
- Ensure efficient time complexity (preferably **O(1)**) for `get` and `put` operations
- Provide **unit tests** demonstrating the correctness of your implementation
- The system should be easily **extendable** to support additional eviction policies in the future

---

## Example Cases

### Example 1 — LRU Policy (Cache Size: 2)

| Operation | Cache State (head → tail) | Result |
|---|---|---|
| `put(1, "A")` | `[1]` | — |
| `put(2, "B")` | `[2, 1]` | — |
| `get(1)` | `[1, 2]` ← 1 moved to head | returns `"A"` |
| `put(3, "C")` cache full | `[3, 1]` | evicts `2` |
| `get(2)` | — | returns `-1` |

### Example 2 — LFU Policy (Cache Size: 2)

| Operation | Key Frequencies | Result |
|---|---|---|
| `put(1, "A")` | `1→freq1` | — |
| `put(2, "B")` | `1→freq1, 2→freq1` | — |
| `get(1)` | `1→freq2, 2→freq1` | returns `"A"` |
| `put(3, "C")` cache full | `1→freq2, 3→freq1` | evicts `2` (lowest freq) |
| `get(2)` | — | returns `-1` |

---

## Optional Enhancements

### 1. Persistent Storage Support
- Option to store cached data on disk (e.g., using SQLite, LevelDB, or RocksDB)
- Upon restart, cache should load existing data from storage

### 2. Time-to-Live (TTL) for Cache Entries
- Allow items to expire automatically after a set TTL (e.g., 5 minutes)
- Implement background cleanup for expired keys

### 3. Multi-threading Support
- Ensure thread-safe cache operations for concurrent `get` and `put` requests
- Use locks, mutex, or concurrent data structures to avoid race conditions

### 4. Cache Persistence via Write-Through & Write-Back
- **Write-through** — every update to the cache should also update persistent storage
- **Write-back** — cache writes updates in batches asynchronously

### 5. Configurable Cache Size
- Allow users to set cache size dynamically at runtime
- Implement a policy to prevent cache from growing uncontrollably

---

---

## Understanding the Problem

A cache is a fixed-size data structure that stores key-value pairs for fast retrieval. When the cache reaches its capacity and a new entry needs to be inserted, an existing entry must be removed. The rule used to decide which entry to remove is called an **Eviction Policy**.

The core challenge here is two things together:
- Each policy needs different **data structures** internally
- The cache itself should not change when you swap policies — this is the **Strategy Pattern**

---

## What the Interviewer is Testing

| Skill | What They Look For |
|---|---|
| Design Patterns | Strategy Pattern for pluggable eviction policies |
| SOLID Principles | OCP — open for extension, closed for modification |
| Data Structures | HashMap + Doubly Linked List for O(1) operations |
| Abstractions | Interface before jumping to implementation |
| Extensibility | Adding new policy without touching Cache core logic |
| Testing | Unit tests covering edge cases, not just happy paths |

---

## Core Concepts

### LRU — Least Recently Used

Evicts the entry that has **not been accessed for the longest time**.

**Data structure:** `HashMap` + `Doubly Linked List`

- Head = most recently used
- Tail = least recently used (eviction candidate)
- `get(key)` → move node to head
- `put(key, value)` → insert at head; if full, remove from tail

**Why doubly linked list?**
When removing a middle node, you need both `node.prev` and `node.next` to reconnect neighbours in O(1). A singly linked list would require O(n) traversal to find the previous node.

---

### LFU — Least Frequently Used

Evicts the entry that has been accessed the **fewest number of times**.
Tiebreaker: among entries with equal frequency, evict the **least recently used** one.

**Data structures needed:**
- `HashMap<key, value>` — O(1) value lookup
- `HashMap<key, frequency>` — track access count per key
- `HashMap<frequency, LinkedHashSet<key>>` — group keys by frequency bucket
- `minFreq` variable — track current minimum frequency for O(1) eviction

On every `get` or `put`, the key's frequency increases by 1 and moves to the next frequency bucket.

---

### FIFO — First In First Out

Evicts the entry that was **inserted first**, regardless of access count.

**Data structure:** `HashMap` + `Queue` (LinkedList or ArrayDeque)

- `put(key)` → enqueue
- Eviction → dequeue from front (oldest entry)

---

## Design Approach

### Identify the Varying Part

The cache's core responsibilities (store, retrieve, manage size) do not change.
What changes is **how you decide which entry to evict**.

This is the **Strategy Pattern** — extract the varying behaviour into an interface, implement each policy separately, and inject it into the cache.

### Class Diagram

```
<<interface>>
EvictionPolicy<K>
  + keyAccessed(key: K)      // called on every get and put
  + evict(): K               // returns the key to remove

LRUEvictionPolicy<K>    implements EvictionPolicy<K>
LFUEvictionPolicy<K>    implements EvictionPolicy<K>
FIFOEvictionPolicy<K>   implements EvictionPolicy<K>

<<interface>>
Cache<K, V>
  + get(key: K): V
  + put(key: K, value: V)
  + setPolicy(policy: EvictionPolicy<K>)

CacheImpl<K, V>   implements Cache<K, V>
  - capacity: int
  - store: HashMap<K, V>
  - policy: EvictionPolicy<K>
```

### Key Design Decisions

| Decision | Choice | Reason |
|---|---|---|
| Eviction interface | Strategy Pattern | Swap policies without touching Cache |
| Storage | `HashMap<K,V>` | O(1) get / put |
| LRU ordering | Doubly Linked List | O(1) move-to-head and remove-from-tail |
| LFU min tracking | `minFreq` variable | O(1) eviction without scanning all keys |
| FIFO ordering | Queue / Deque | O(1) enqueue and dequeue |
| Thread safety (optional) | `ReentrantReadWriteLock` | Concurrent reads, exclusive writes |

---

## SOLID Principles Mapping

| Principle | How it applies here |
|---|---|
| **S** — Single Responsibility | `Cache` manages storage. `EvictionPolicy` manages eviction. Two separate concerns. |
| **O** — Open/Closed | Add a new policy (e.g. MRU) by creating a new class. Zero changes to `Cache` or existing policies. |
| **L** — Liskov Substitution | Any `EvictionPolicy` implementation can replace another without breaking `Cache`. |
| **I** — Interface Segregation | `EvictionPolicy` has only two methods. No fat interface forcing unused implementations. |
| **D** — Dependency Inversion | `Cache` depends on `EvictionPolicy` interface, not on `LRUEvictionPolicy` directly. |

---

## Time Complexity

| Operation | LRU | LFU | FIFO |
|---|---|---|---|
| `get(key)` — found | O(1) | O(1) | O(1) |
| `get(key)` — not found | O(1) | O(1) | O(1) |
| `put(key)` — key exists | O(1) | O(1) | O(1) |
| `put(key)` — new, no eviction | O(1) | O(1) | O(1) |
| `put(key)` — eviction needed | O(1) | O(1) | O(1) |
| Space | O(n) | O(n) | O(n) |

> All operations must be O(1). If your design requires scanning, revisit the data structure choice.

---

## Edge Cases to Handle

- `get` on an empty cache
- `get` on a key that was evicted
- `put` with duplicate key — should **update value**, not increase size
- `put` when capacity is 1 — every new key evicts the previous
- LFU tiebreaker — two keys with same frequency, evict least recently used among them
- Switch eviction policy mid-operation — existing data must remain intact
- Capacity set to 0 — define behaviour clearly (throw exception or no-op)

---

## Unit Test Checklist

| Test Case | Policy | Expected Behaviour |
|---|---|---|
| `get` on missing key | All | Returns `-1` |
| `put` and `get` same key | All | Returns correct value |
| Duplicate `put` updates value | All | `get` returns new value, size unchanged |
| Eviction when full | LRU | Least recently accessed key removed |
| Recency updates on `get` | LRU | Accessed key not evicted even if old |
| Eviction when full | LFU | Lowest frequency key removed |
| LFU tiebreaker | LFU | Among equal freq, LRU one is evicted |
| Eviction when full | FIFO | Oldest inserted key removed |
| FIFO ignores access order | FIFO | Accessing a key does not delay its eviction |
| Policy switch at runtime | All | After switch, new policy governs evictions |

---

## Implementation Plan

| Day | Task | What to Build |
|---|---|---|
| Day 1 | Interfaces + LRU | `EvictionPolicy` interface, `DoublyLinkedList`, `LRUEvictionPolicy`, `CacheImpl` |
| Day 2 | LFU + FIFO | `LFUEvictionPolicy` with frequency buckets, `FIFOEvictionPolicy` with Queue |
| Day 3 | Unit Tests | Tests for all three policies, edge cases, and policy switching |
| Day 4 *(optional)* | Enhancements | TTL expiry, thread safety, persistent storage, dynamic capacity |

---

*LLD Interview Preparation — Cache System Assignment — SDE-1 / SDE-2 Level*