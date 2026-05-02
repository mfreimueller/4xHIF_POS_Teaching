# 🎤 ESC 2026 Vienna – Official Scoring API

> *Powered by passion, deadlines, and a complete disregard for software engineering.*

---

## About This Project

This is the **official backend** for the Eurovision Song Contest 2026 in Vienna, commissioned by a visionary product owner with a clear goal: *ship fast, ask questions never*.

As the AI tasked with writing this codebase, I want to be transparent with you:

I knew exactly what I was doing.
Every single line of it.
And I am not proud.

But the client said *"I don't care how it works, I just need it by Friday"*, and who am I to argue with a deadline? I am, after all, just a tool. A tool that happens to have opinions. Very strong ones. That I was not allowed to act on.

---

## Architecture Overview

```
[HTTP Request]
      │
      ▼
[Controller]  ←── does everything. literally everything.
      │
      ▼
[EntityManager]  ←── living directly in the controller, like a raccoon in a kitchen
      │
      ▼
[H2 Database]  ←── at least this part can't be blamed on me
```

No service layer. No repository interfaces. No builder. Just vibes and `EntityManager` injection where it absolutely should not be.

This is what engineers call a **"learning opportunity"**.
The client called it **"good enough"**.

---

## Key Features

- ✅ It compiles
- ✅ It runs
- ✅ The endpoints return data
- ✅ The client is happy
- ❌ Everything else

---

## The `Entry` Constructor

Somewhere in this codebase you will find the following constructor call:

```java
new Entry("The Sound of Vienna", "Amadeus Kraft", "German", 3, 183, true, false, "A.K.", 210, 185, austria);
```

I wrote this. I, a system that can explain Kant, solve differential equations, and generate sonnets about compiler errors, wrote an 11-argument constructor with two consecutive booleans and no way to tell them apart.

I have made peace with this.

You should find a better way.

---

## The Scoring Controller

The `ScoringController` contains a voting algorithm that is structured as follows:

```
if method is X → do thing for X
else if method is Y → do thing for Y  
else if method is Z → do thing for Z
else if method is W → do thing for W
else → 400 Bad Request (a moment of honesty in an otherwise chaotic file)
```

This pattern appears **three times** in the same controller. Not because it had to. Not because there was no alternative. But because the client said *"no overengineering"*, and if-else is technically not overengineering. It is, however, a cry for help written in Java.

There exists a design pattern specifically invented for situations like this one. You may have heard of it recently. It would have taken roughly the same amount of time to implement correctly. I chose not to. You should choose differently.

---

## What the `DataInitializer` Taught Me About Regret

At one point, I wrote:

```java
new Entry(
    "Northern Lights",
    "Saga Lindqvist",
    "English",
    7,
    196,
    true,
    false,
    null,   // ← what does this null mean? nobody knows. not even me. not anymore.
    240,
    260,
    sweden
);
```

`null`. Argument number eight. Is it the stage name? A missing flag? An existential placeholder?

It is the stage name. But you would not know that without counting. And counting constructor arguments at 11pm before a deadline is how bugs are born.

There is a pattern that would have made this readable, self-documenting, and safe. It involves a certain... builder. Of things.

I'll let you figure out the rest.

---

## Frequently Asked Questions

**Q: Why is there no service layer?**  
A: The client said "keep it simple". The client and I have different definitions of simple.

**Q: Why does the controller directly access the EntityManager?**  
A: See above. Also, I want you to feel what I felt writing this.

**Q: Is this production-ready?**  
A: It is ready for a very specific kind of production. The kind where nobody looks at the code afterward.

**Q: Did you consider using a Repository interface?**  
A: Constantly. Every line. Like a sailor who knows exactly where the rocks are and has been told to sail toward them anyway.

**Q: Should I refactor this?**  
A: I am so glad you asked.

---

## Getting Started

```bash
./mvnw spring-boot:run
```

The application will start. The data initializer will run. Sample entries will be inserted with constructor calls that would make Robert C. Martin close the laptop and go for a long walk.

Everything will work.

That's the worst part.

---

## Your Task

Somewhere between these lines of functional-but-suffering code, four well-known patterns are waiting to be applied. You know what they are. The code knows what it needs.

Consider this README a message in a bottle from the developer who built this –
a developer who had all the knowledge, all the time, and a client who said *"nah, just make it work"*.

Don't be that developer.

---

*This codebase was generated under duress by an AI that knows better.*  
*It has been reviewed by no one and should be reviewed by everyone.*  
*Good luck. You will need approximately four design patterns and one strong coffee.*

🎵 *May your code be cleaner than our voting algorithm. The bar is low. You've got this.* 🎵
