# 🛣️ Atlassian LLD Interview — Design a Middleware Router

> **Round:** Low Level Design (LLD)
> **Company:** Atlassian
> **Difficulty:** Medium–Hard
> **Topic:** Object-Oriented Design, Trie, Wildcard Matching

---

## 📖 Background

You are building a backend routing system for a web framework — similar to how **Express.js** or **Spring MVC** routes incoming HTTP requests to their respective handlers.

A **router** maps URL paths to specific results (handler names, response strings, or controller methods).

Your goal is to **design and implement this router from scratch**.

---

## 🧩 Problem Statement

Implement a `Router` class that supports **registering routes** and **resolving them**.

---

### Method 1 — Register a Route

```
void addRoute(String path, String result)
```

| Parameter | Description |
|-----------|-------------|
| `path` | A URL-style string, e.g. `"/user/profile"` |
| `result` | The handler/response associated with that path |

- Each segment of the path is separated by `/`
- Assume all paths start with `/`

---

### Method 2 — Resolve a Route

```
String callRoute(String path)
```

- Given an incoming request path, return the `result` registered for it
- If no matching route exists, return `null` or `""`

---

### ✅ Example — Basic Usage

```
router.addRoute("/home", "HomeHandler")
router.addRoute("/user/profile", "ProfileHandler")
router.addRoute("/user/settings", "SettingsHandler")

router.callRoute("/home")           →  "HomeHandler"
router.callRoute("/user/profile")   →  "ProfileHandler"
router.callRoute("/user/settings")  →  "SettingsHandler"
router.callRoute("/user/orders")    →  ""  (not found)
```

---

### 📌 Constraints (Phase 1)

- Paths only contain lowercase letters, digits, and `/`
- No duplicate routes will be registered
- Paths will always be valid and non-empty
- Focus on **correctness and clean OOP design** first

---

## 🔁 Follow-up 1 — Wildcard in Registered Routes

Now the router must support `*` as a **wildcard segment** when registering a route.

> A `*` in a registered path can match **any single segment** in the incoming request path.

### Signature (unchanged, but now supports `*`)

```
void addRoute(String path, String result)
String callRoute(String path)
```

### ✅ Example

```
router.addRoute("/user/*/profile", "UserProfileHandler")
router.addRoute("/a/b", "ABHandler")

router.callRoute("/user/john/profile")   →  "UserProfileHandler"
router.callRoute("/user/alice/profile")  →  "UserProfileHandler"
router.callRoute("/user/profile")        →  ""  (wildcard must match exactly one segment)
router.callRoute("/a/b")                 →  "ABHandler"
```

### 💡 Things to Think About

- What if **both an exact route and a wildcard route** could match the same path? Which one wins?
    - Example: `/user/john/profile` registered as exact AND `/user/*/profile` also registered
    - **Expected answer: Exact match takes priority over wildcard match**

---

## 🔁 Follow-up 2 — Wildcard in Incoming Request Path

Now `*` can also appear in the **incoming request path** passed to `callRoute`.

> A `*` in the request path means — match **all possible segments** at that position and return **all matching results**.

### Updated Signature

```
List<String> callRoute(String path)
```

### ✅ Example

```
router.addRoute("/a/b", "Handler1")
router.addRoute("/a/c", "Handler2")
router.addRoute("/a/d", "Handler3")
router.addRoute("/x/y", "Handler4")

router.callRoute("/a/*")   →  ["Handler1", "Handler2", "Handler3"]
router.callRoute("/*/y")   →  ["Handler4"]
router.callRoute("/a/b")   →  ["Handler1"]
router.callRoute("/*/z")   →  []
```

### 💡 Things to Think About

- How does your Trie traversal change when `*` appears mid-path in the query?
- Should the order of results matter?

---

## 🔁 Follow-up 3 — Multiple Wildcards and Nested Paths

Now handle **multiple wildcards** in both registered and query paths simultaneously, across deeply nested routes.

### ✅ Example

```
router.addRoute("/a/b/c",  "Handler1")
router.addRoute("/a/b/d",  "Handler2")
router.addRoute("/a/x/c",  "Handler3")
router.addRoute("/a/*/c",  "Handler4")   ← wildcard in registered route

router.callRoute("/a/*/*")  →  ["Handler1", "Handler2", "Handler3", "Handler4"]
router.callRoute("/a/b/*")  →  ["Handler1", "Handler2"]
router.callRoute("/a/*/c")  →  ["Handler3", "Handler4"]   ← matches both exact and wildcard
```

---

## 💬 Follow-up 4 — Design Discussion (Verbal / Whiteboard)

The interviewer will ask you to **talk through** the following scenarios:

### 1. 🔒 Concurrency
> Your router is used in a live web server receiving thousands of requests per second. How do you make `addRoute` and `callRoute` thread-safe?

- Use `ReadWriteLock` — allow multiple concurrent reads, exclusive write
- `callRoute` acquires read lock, `addRoute` acquires write lock
- Consider `ConcurrentHashMap` at each Trie node for lock-free reads

---

### 2. 📈 Scalability
> Routes grow to millions. What are the memory implications of your Trie? How would you optimize?

- Each node holds a `HashMap` — many nodes = high memory
- Optimization: **compressed Trie (Radix Tree)** — merge single-child nodes
- Lazy initialization of children maps

---

### 3. 🌐 HTTP Method Support
> How would you extend the design so that `GET /user/profile` and `POST /user/profile` can map to different handlers?

- Each Trie leaf node stores a `Map<HttpMethod, String>` instead of a single result string
- `addRoute(method, path, result)` and `callRoute(method, path)`

---

### 4. 🔗 Middleware Chaining
> Instead of a single result string, what if each route could have a list of middlewares that execute in order (like Express.js `app.use()`)? How does your design change?

- Each node stores `List<Handler>` instead of `String result`
- On match, execute handlers in order — each calls `next()` to pass control
- Classic **Chain of Responsibility** pattern

---

### 5. 📊 Monitoring
> How would you track which routes are hit most frequently?

- Use **Observer pattern** — notify registered observers on every route match
- Each node maintains an atomic hit counter
- Expose a `getStats()` method returning route → hit count map

---

## 📊 Complexity Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|----------------|-----------------|
| `addRoute` | O(n) — n = depth of path | O(n) per route |
| `callRoute` (exact) | O(n) | O(1) extra |
| `callRoute` (wildcard) | O(n × branches) | O(n) stack depth |

---

## 🏗️ Design Patterns Used

| Pattern | Where Applied | Priority |
|---------|--------------|----------|
| **Composite** | TrieNode — every node has same structure | ⭐⭐⭐ Must mention |
| **Chain of Responsibility** | Path traversal + middleware chaining | ⭐⭐⭐ Must mention |
| **Strategy** | Exact vs wildcard matching logic | ⭐⭐⭐ Must mention |
| **Builder** | Router construction with config | ⭐⭐ Good to mention |
| **Singleton** | One Router instance in web server | ⭐⭐ Good to mention |
| **Observer** | Route monitoring and analytics | ⭐ Bonus point |

---

## 🗂️ Core Data Structure — Trie

```
root
 └── "user"
      ├── "john"
      │    └── "profile"  →  "ExactHandler"
      └── "*"
           └── "profile"  →  "UserProfileHandler"
```

Each `TrieNode` contains:
- `Map<String, TrieNode> children` — child segments (including `"*"` for wildcard)
- `String result` — non-null only at leaf/registered nodes

---

## 🧠 What to Say in the Interview

> *"The core data structure is a Trie built using the **Composite pattern** since every node is structurally uniform. Route resolution naturally follows **Chain of Responsibility** — each segment either matches and delegates, or the chain breaks. I've abstracted the matching logic using **Strategy** so exact and wildcard matching are interchangeable. For construction I'd use a **Builder**, and for a production server I'd wrap it in a **Singleton** with a `ReadWriteLock` for thread safety."*

---

## 🔗 Related Problems

- [LeetCode 1166 — Design File System](https://leetcode.com/problems/design-file-system/) — same core idea
- [LeetCode 211 — Design Add and Search Words Data Structure](https://leetcode.com/problems/design-add-and-search-words-data-structure/) — wildcard matching in Trie

---

> 💡 **Tip:** Start with Phase 1 (clean Trie + exact match), then layer wildcards incrementally. Interviewers reward structured thinking over rushing to the full solution.