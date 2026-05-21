# Creational Design Patterns — Revision Notes

> **Target Level:** SDE-1 to SDE-2 Interview Preparation
> **Category:** Low-Level Design (LLD)
> **Prerequisites:** OOP basics, SOLID principles

---

## Table of Contents

1. [Introduction](#introduction)
2. [Why Creational Patterns?](#why-creational-patterns)
3. [The Five Patterns](#the-five-patterns)
   - [Singleton](#1-singleton-pattern)
   - [Factory Method](#2-factory-method-pattern)
   - [Abstract Factory](#3-abstract-factory-pattern)
   - [Builder](#4-builder-pattern)
   - [Prototype](#5-prototype-pattern)
4. [Quick Comparison](#quick-comparison)
5. [Interview Cheat Sheet](#interview-cheat-sheet)
6. [Common Pitfalls](#common-pitfalls)
7. [Practice Problems](#practice-problems)
8. [Further Reading](#further-reading)

---

## Introduction

**Creational design patterns** deal with **object creation mechanisms**. They abstract the instantiation process, making a system independent of how its objects are created, composed, and represented.

Instead of using `new ClassName()` directly everywhere, these patterns provide controlled, flexible ways to create objects.

---

## Why Creational Patterns?

Direct instantiation (`new`) becomes problematic when:

- Object creation involves **complex logic** (e.g., validations, setup)
- You need to **control the number of instances** (e.g., only one DB connection)
- The exact **type isn't known until runtime**
- You want to **decouple** client code from concrete classes
- An object has **many optional parameters** (telescoping constructor problem)
- Object creation is **expensive** (cloning is cheaper)

---

## The Five Patterns

### 1. Singleton Pattern

**Intent:** Ensure a class has **only one instance** and provide a global access point to it.

#### When to Use
- Database connection pool
- Logger
- Configuration manager
- Cache
- Thread pool

#### Structure
```
┌─────────────────────────┐
│      Singleton          │
├─────────────────────────┤
│ - instance: Singleton   │  ← static, private
├─────────────────────────┤
│ - Singleton()           │  ← private constructor
│ + getInstance()         │  ← static method
└─────────────────────────┘
```

#### Java Implementation (Thread-Safe with Double-Checked Locking)

```java
public class Logger {
    // volatile ensures visibility across threads
    private static volatile Logger instance;

    // Private constructor prevents external instantiation
    private Logger() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method");
        }
    }

    public static Logger getInstance() {
        if (instance == null) {                    // First check (no lock)
            synchronized (Logger.class) {
                if (instance == null) {            // Second check (with lock)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}
```

#### Different Singleton Approaches

| Approach | Thread-Safe? | Lazy? | Notes |
|----------|:---:|:---:|-------|
| Eager Initialization | ✅ | ❌ | Instance created at class loading |
| Lazy Initialization | ❌ | ✅ | Not safe in multi-threaded environment |
| Synchronized Method | ✅ | ✅ | Performance hit due to locking |
| Double-Checked Locking | ✅ | ✅ | Best performance, needs `volatile` |
| Bill Pugh (Static Inner Class) | ✅ | ✅ | **Recommended** — clean & efficient |
| Enum Singleton | ✅ | ❌ | Most robust against reflection/serialization |

#### Bill Pugh Singleton (Recommended)

```java
public class Logger {
    private Logger() {}

    private static class Holder {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() {
        return Holder.INSTANCE;
    }
}
```

#### Pros & Cons
✅ Controlled access to single instance
✅ Saves memory
❌ Can violate **Single Responsibility Principle**
❌ Hard to unit test (global state)
❌ Hidden dependencies

---

### 2. Factory Method Pattern

**Intent:** Define an interface for creating an object, but let subclasses (or a factory method) decide which class to instantiate.

#### When to Use
- Notification systems (Email, SMS, Push)
- Payment gateways (UPI, CreditCard, NetBanking)
- Document parsers (PDF, Word, Excel)
- Database drivers

#### Structure
```
Client → Factory → Product Interface
                       ↓
              ┌────────┼────────┐
         ConcreteA  ConcreteB  ConcreteC
```

#### Java Implementation

```java
// 1. Product Interface
interface Notification {
    void send(String message);
}

// 2. Concrete Products
class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}

class SMSNotification implements Notification {
    public void send(String message) {
        System.out.println("SMS sent: " + message);
    }
}

class PushNotification implements Notification {
    public void send(String message) {
        System.out.println("Push sent: " + message);
    }
}

// 3. Factory
class NotificationFactory {
    public static Notification createNotification(String type) {
        switch (type.toUpperCase()) {
            case "EMAIL": return new EmailNotification();
            case "SMS":   return new SMSNotification();
            case "PUSH":  return new PushNotification();
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}

// 4. Usage
public class Main {
    public static void main(String[] args) {
        Notification n = NotificationFactory.createNotification("EMAIL");
        n.send("Hello!");
    }
}
```

#### Pros & Cons
✅ Follows **Open/Closed Principle** — add new types without changing client code
✅ Decouples client from concrete classes
✅ Centralizes object creation logic
❌ Code complexity increases with too many products
❌ Requires creating a new subclass for each product variant (in classical GoF version)

---

### 3. Abstract Factory Pattern

**Intent:** Provide an interface for creating **families of related objects** without specifying their concrete classes. It's a "factory of factories."

#### When to Use
- Cross-platform UI toolkits (Windows/Mac/Linux components)
- Multi-database support (MySQL/PostgreSQL/Oracle families)
- Theming systems (Dark/Light theme components)

#### Factory Method vs Abstract Factory

| Factory Method | Abstract Factory |
|---|---|
| Creates **one** product | Creates **family** of related products |
| Uses inheritance | Uses composition |
| Single method | Multiple methods |

#### Java Implementation

```java
// 1. Abstract Products
interface Button { void render(); }
interface Checkbox { void render(); }

// 2. Concrete Products — Windows family
class WindowsButton implements Button {
    public void render() { System.out.println("Windows Button"); }
}
class WindowsCheckbox implements Checkbox {
    public void render() { System.out.println("Windows Checkbox"); }
}

// 3. Concrete Products — Mac family
class MacButton implements Button {
    public void render() { System.out.println("Mac Button"); }
}
class MacCheckbox implements Checkbox {
    public void render() { System.out.println("Mac Checkbox"); }
}

// 4. Abstract Factory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// 5. Concrete Factories
class WindowsFactory implements GUIFactory {
    public Button createButton()     { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

class MacFactory implements GUIFactory {
    public Button createButton()     { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

// 6. Client Code
public class Application {
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void renderUI() {
        button.render();
        checkbox.render();
    }

    public static void main(String[] args) {
        GUIFactory factory = new MacFactory();   // Decided at runtime
        new Application(factory).renderUI();
    }
}
```

#### Pros & Cons
✅ Ensures products from the same family are used together
✅ Easy to switch entire product families
✅ Follows Open/Closed Principle
❌ Code becomes complex with many interfaces
❌ Adding a new product to an existing family requires changing all factory interfaces

---

### 4. Builder Pattern

**Intent:** Construct **complex objects step-by-step**. Useful when an object has many optional parameters. Solves the "telescoping constructor" anti-pattern.

#### When to Use
- Objects with many optional fields (User profile, Pizza, Resume)
- Building SQL queries, HTTP requests
- Immutable objects with many parameters
- StringBuilder in Java (real-world example)

#### The Problem It Solves (Telescoping Constructor)

```java
// ❌ BAD — Telescoping constructor anti-pattern
public Pizza(String size) { ... }
public Pizza(String size, boolean cheese) { ... }
public Pizza(String size, boolean cheese, boolean pepperoni) { ... }
public Pizza(String size, boolean cheese, boolean pepperoni, boolean olives) { ... }
// ...and so on. Becomes unmanageable.
```

#### Java Implementation

```java
public class Pizza {
    // All fields are final → Immutable object
    private final String size;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;
    private final boolean olives;

    // Private constructor — only Builder can call it
    private Pizza(Builder builder) {
        this.size       = builder.size;
        this.cheese     = builder.cheese;
        this.pepperoni  = builder.pepperoni;
        this.mushrooms  = builder.mushrooms;
        this.olives     = builder.olives;
    }

    // Static nested Builder class
    public static class Builder {
        private final String size;       // required
        private boolean cheese;          // optional
        private boolean pepperoni;
        private boolean mushrooms;
        private boolean olives;

        public Builder(String size) {    // required in constructor
            this.size = size;
        }

        public Builder addCheese()     { this.cheese = true; return this; }
        public Builder addPepperoni()  { this.pepperoni = true; return this; }
        public Builder addMushrooms()  { this.mushrooms = true; return this; }
        public Builder addOlives()     { this.olives = true; return this; }

        public Pizza build() {
            return new Pizza(this);
        }
    }
}

// Usage — clean, readable, fluent API
Pizza pizza = new Pizza.Builder("Large")
                  .addCheese()
                  .addPepperoni()
                  .addOlives()
                  .build();
```

#### Pros & Cons
✅ Readable code with method chaining
✅ Immutable objects (thread-safe)
✅ No need for multiple constructors
✅ Can enforce required fields via Builder's constructor
❌ More code (Builder class needed for each product)
❌ Doubles the lines of code per class

#### Real-World Examples
- `StringBuilder` / `StringBuffer`
- Lombok's `@Builder` annotation
- `Stream.Builder` in Java 8+

---

### 5. Prototype Pattern

**Intent:** Create new objects by **cloning an existing object** (the prototype) instead of creating from scratch. Useful when object creation is expensive.

#### When to Use
- Object creation is costly (DB query, network call, complex computation)
- You need many similar objects with slight variations
- Game development (cloning enemies, bullets, particles)
- Caching frequently-used configurations

#### Java Implementation

```java
abstract class Document implements Cloneable {
    protected String content;
    protected String author;

    @Override
    public Document clone() {
        try {
            return (Document) super.clone();    // Shallow copy
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void setContent(String content) { this.content = content; }
    public abstract void show();
}

class Resume extends Document {
    public Resume(String author) {
        this.author = author;
        this.content = "Default Resume Template";
        // Imagine expensive operations: DB fetch, formatting, etc.
    }

    public void show() {
        System.out.println("Author: " + author + " | Content: " + content);
    }
}

// Usage
Resume original = new Resume("Rahul");     // expensive
Resume copy = (Resume) original.clone();   // cheap
copy.setContent("Customized for Google");

original.show();  // Author: Rahul | Content: Default Resume Template
copy.show();      // Author: Rahul | Content: Customized for Google
```

#### Shallow Copy vs Deep Copy ⚠️ (Common Interview Question)

| Shallow Copy | Deep Copy |
|---|---|
| Copies primitive fields | Copies primitive fields |
| Nested objects share **same reference** | Nested objects are also **cloned** |
| Fast but risky if nested objects are mutable | Slower but safer |
| `super.clone()` does shallow by default | Must implement manually |

**Deep Copy Example:**

```java
class Address implements Cloneable {
    String city;
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }
}

class Employee implements Cloneable {
    String name;
    Address address;

    public Employee clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();
        cloned.address = this.address.clone();  // Deep copy nested object
        return cloned;
    }
}
```

#### Pros & Cons
✅ Avoids expensive object creation
✅ Simpler than rebuilding object state
✅ Adds/removes objects at runtime
❌ Cloning complex objects with circular references is tricky
❌ Deep copy implementation can be tedious

---

## Quick Comparison

| Pattern | Purpose | Key Idea | Real Example |
|---------|---------|----------|--------------|
| **Singleton** | One instance only | Restrict instantiation | `Runtime.getRuntime()` |
| **Factory Method** | Create one product | Delegate creation to a method | `Calendar.getInstance()` |
| **Abstract Factory** | Create related product families | Factory of factories | Java AWT `Toolkit` |
| **Builder** | Build complex objects step-by-step | Fluent construction | `StringBuilder` |
| **Prototype** | Clone existing objects | Copy instead of create | `Object.clone()` |

---

## Interview Cheat Sheet

### Quick Identification (When to Use What)

| Scenario | Pattern |
|----------|---------|
| "Only one DB connection should exist" | Singleton |
| "Send notification via Email or SMS based on user choice" | Factory Method |
| "App should support Windows, Mac, Linux UI components" | Abstract Factory |
| "Create User object with name, email, age, address, phone (all optional)" | Builder |
| "Generate 1000 similar enemy objects in a game" | Prototype |

### Common Interview Questions

1. **What's the difference between Factory Method and Abstract Factory?**
   → Factory Method creates one product; Abstract Factory creates a family of related products.

2. **How would you make Singleton thread-safe?**
   → Double-checked locking with `volatile`, Bill Pugh pattern, or enum singleton.

3. **Why use Builder over telescoping constructors?**
   → Better readability, optional parameters, immutability, fluent API.

4. **Difference between shallow and deep copy?**
   → Shallow shares nested object references; deep copies everything recursively.

5. **Can Singleton be broken?**
   → Yes — via reflection, serialization, or cloning. Enum singleton prevents all three.

6. **Which SOLID principles do these patterns support?**
   → Factory & Abstract Factory → Open/Closed Principle
   → Builder → Single Responsibility (separates construction from representation)
   → Singleton may **violate** SRP (responsibility + instance management)

---

## Common Pitfalls

### ❌ Singleton Anti-Patterns
- Using Singleton as a global variable substitute
- Not handling multi-threading properly
- Forgetting that Singleton makes unit testing difficult

### ❌ Factory Overuse
- Creating a factory for objects that don't need one
- Adding too many `if-else` / `switch` branches → consider Strategy pattern

### ❌ Builder Misuse
- Using Builder for simple objects with 2–3 fields (overkill)
- Forgetting to validate required fields in `build()` method

### ❌ Prototype Pitfalls
- Doing shallow copy when deep copy is needed
- Not implementing `Cloneable` properly
- Circular references during deep cloning

---

## Practice Problems

Try implementing these to solidify understanding:

1. **Singleton:** Design a thread-safe `DatabaseConnection` class.
2. **Factory:** Build a `ShapeFactory` that creates Circle, Square, Triangle.
3. **Abstract Factory:** Design a `VehicleFactory` for `Car` and `Bike`, with variants `Petrol` and `Electric`.
4. **Builder:** Create a `User` class with fields (name, email, age, address, phone) using Builder.
5. **Prototype:** Implement a `GameCharacter` clone system with deep copying of inventory.

---

## Further Reading

- **Book:** *Design Patterns: Elements of Reusable Object-Oriented Software* — Gang of Four (GoF)
- **Book:** *Head First Design Patterns* — Eric Freeman (beginner-friendly)
- **Online:** [Refactoring.Guru — Creational Patterns](https://refactoring.guru/design-patterns/creational-patterns)
- **Online:** [SourceMaking — Design Patterns](https://sourcemaking.com/design_patterns)

---

## Revision Strategy

🔄 **Before any LLD/System Design interview:**
1. Re-read the Quick Comparison table
2. Solve at least 1 practice problem
3. Review the "Common Interview Questions" section
4. Be ready to code Singleton (thread-safe) and Builder from memory

---

**Last Updated:** May 2026
**Author:** [Sachin]
**Tags:** `design-patterns` `lld` `system-design` `interview-prep` `oop`