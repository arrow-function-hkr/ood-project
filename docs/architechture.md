# Architecture

This document describes the architectural decisions the group made for the "Sustainable Product and Recycling Management System" project under the "Object-Oriented Design" course and the reasoning behind each one. It is meant to explain <u>**why**</u> the system is structured the way it is, not just <u>**what**</u> the structure is.


## Architectural Style

We chose a layered architecture with three layers inside the `onion.lifeproducts.rms` package: `presentation`, `application`, and `domain`. `Main` (outside the package) wires the presentation layer together and starts the menu loop which powers the whole application.

```
┌──────────────────────────────────────────────────────────┐
│                       Presentation                       │
│ ConsoleUI, Data, Util, ANSI, Lambda, ConsoleUIEntry,     │
│ ConsoleUIEntryChecker, ConsoleUIANSIOptions              │
└──────────────────────────────────────────────────────────┘
                             │
                           calls
                             v
┌──────────────────────────────────────────────────────────┐
│                        Application                       │
│ ApplicationService, StoragePool, RecyclingService        │
└──────────────────────────────────────────────────────────┘
                             │
                           calls
                             v
┌──────────────────────────────────────────────────────────┐
│                          Domain                          │
│ Product, Material, RecyclingGuidance, RecyclingCategory, │
│ ImpactReport, ImpactCalculationStrategyInterface,        │
│ SimpleImpactCalculationStrategy,                         │
│ WeightPlusLifespanImpactCalculationStrategy              │
└──────────────────────────────────────────────────────────┘
```

Dependencies flow strictly top to down. The presentation layer calls only the application layer, and the application layer calls only the domain layer. The domain layer has no dependency on any other layer. This rule is enforced at the package level and is the foundation that the rest of the design decisions build on. This allows us to swap layers without modifying existing ones which allows for dynamic development and maintenance of the code.


## Layer Responsibilities

  - Presentation &mdash; `onion.lifeproducts.rms.presentation/`: All I/O with the user.

    `ConsoleUI` runs the menu loop

    `Data` defines menu entries and their callbacks.

    Supporting utilities include `Util` (text formatting and output), `ANSI` (terminal colour codes), `Lambda`, `ConsoleUIEntry`, `ConsoleUIEntryChecker`, and `ConsoleUIANSIOptions` to enforce SRP on the `ConsoleUI` class and separate other responsibilities.

  - Application &mdash; `onion.lifeproducts.rms.application/`: Mediates between presentation and domain

    `ApplicationService` is the single entry point for the presentation layer.

    `StoragePool` stores `Product`, `Material`, and `RecyclingGuidance` objects in memory.

    `RecyclingService` coordinates impact calculation workflows.

  - Domain &mdash; `onion.lifeproducts.rms.domain/`: Core logic

    `Product`, `Material`, and `RecyclingGuidance` model the domain entities.

    `RecyclingCategory` is an enum that stores available types of the materials.

    `Material` owns one `RecyclingGuidance` and one `RecyclingCategory`.

    `ImpactCalculationStrategyInterface` and its two implementations define the calculation algorithms.

    `ImpactReport` carries results of the recycling.


## Design Decisions

### Why three layers

We wanted each part of the system to have one clear reason to change. I/O concerns (menu loop, ANSI formatting, prompts) change for very different reasons than business rules (how impact is calculated, how products relate to materials). Mixing them would make both harder to evolve and test. Three layers also let us replace one layer entirely (for example, swap the console UI for a GUI, or replace `StoragePool` with a database) without touching the others.


### Why `ApplicationService` is the single entry point

The presentation layer calls only `ApplicationService`. It never reaches into `StoragePool` or the domain classes directly. This was a deliberate decision. If the presentation layer depended on multiple application-layer classes, every change in those classes would risk breaking the UI. Concentrating the contract in one class keeps the surface area small, predictable, and easy to cover with unit tests if applicable.


### Why `ApplicationService` exposes a static API

The presentation layer should not have to construct or manage application-layer instances. Making the `ApplicationService` API static gives the presentation layer a stable, instance-free entry point and keeps the wiring inside `Main` minimal.


### Why in-memory storage

We deliberately scoped the project to object-oriented design rather than persistence and real-world use. `StoragePool` keeps everything in memory during the execution. The fact that storage is accessed only through `ApplicationService`, swapping it for a database later would not affect the presentation or domain layers.


### Why materials are reused, not re-created

The non-functional requirement is that materials are shared across products. When a product is added, `ApplicationService.resolveMaterials()` looks each material up in `StoragePool` by ID and reuses the same `Material` instance instead of constructing a new one. This guarantees that all products referencing "Aluminium" point at the same object, which keeps environmental impact data consistent across the system and consumes less memory in long run.


### Why the Strategy pattern for impact calculation

The system needs to support multiple ways of calculating environmental impact, and one of the non-functional requirements is that adding a new algorithm must not modify `Product`. The Strategy pattern fits exactly: each algorithm is its own class implementing `ImpactCalculationStrategyInterface`, and `RecyclingService` receives the chosen strategy through its constructor. See [Strategy Pattern](#strategy-pattern) below for details.


### Why dependencies flow top to down only

Allowing the domain layer to depend on the application or presentation layers would make the business rules harder to test in isolation and would couple the core logic to incidental concerns like console formatting. Forcing the dependency direction top to down means the domain layer can be reused or moved without dragging the rest of the system along since the dependency is not domain layer on application layer or above, but in reverse direction.


## Design Principles


### Single Responsibility Principle (SRP)

Each layer and each class has one clear reason to change. `ConsoleUI` handles I/O, `ApplicationService` mediates, `StoragePool` stores, `RecyclingService` coordinates recycling, the strategy classes calculate impact. 


### Open/Closed Principle (OCP)

New impact calculation algorithms can be added by writing a new class that implements `ImpactCalculationStrategyInterface`. Existing classes (`RecyclingService`, `Product`, `Material`) do not need to be modified. 


### Liskov Substitution Principle (LSP)

Both `SimpleImpactCalculationStrategy` and `WeightPlusLifespanImpactCalculationStrategy` are usable wherever `ImpactCalculationStrategyInterface` is expected, with identical method contracts. 


### Dependency Inversion Principle (DIP)

`RecyclingService` depends on the abstraction `ImpactCalculationStrategyInterface`, not on a concrete strategy. The concrete strategy is injected via the constructor at the runtime. 


## Strategy Pattern

Environmental impact calculation uses the **Strategy pattern** to allow switching between calculation algorithms at runtime without modifying `Product` or `RecyclingService`. This satisfies the non-functional requirement that the strategy must be replaceable without changing `Product`, and it is the project's clearest application of OCP and DIP.

`ImpactCalculationStrategyInterface` defines the contract. Two concrete implementations are provided:

- `SimpleImpactCalculationStrategy` - multiplies each material's amount by its emission factor and sums the results.
- `WeightPlusLifespanImpactCalculationStrategy` - divides the total material impact by the product's lifespan in days (from manufacture date to end-of-life date), giving a per-day impact figure that accounts for how long the product is in use.

`ApplicationService.createImpactCalculationStrategy(int strategyId)` selects the correct implementation based on the user's input and injects it into a new `RecyclingService` via its constructor. Adding a third algorithm in the future requires writing one new class and registering its ID. No existing classes need to change.


## Technical Debt and Trade-offs

While the system design emphasizes maintainability and extensibility, several deliberate compromises were made, resulting in technical debt and trade-offs worth noting.


### Static `ApplicationService`

The static API of `ApplicationService` simplifies access from the presentation layer and minimizes wiring in `Main`.

**Trade-offs / Debt:**

- Makes unit testing and mocking more difficult, as static methods cannot be easily substituted with test doubles.

- Introduces a form of global state, which could lead to tighter coupling if the class grows over time.

- Reduces flexibility if dependency injection is later desired for more complex workflows.


### In-Memory `StoragePool`

Using an in-memory storage layer kept the project focused on object-oriented design rather than persistence.

**Trade-offs / Debt:**

- All data is lost when the application terminates, limiting real-world usefulness.

- No support for concurrent access, which could be an issue in multi-threaded extensions.

- Scaling to larger datasets or integrating with a database later would require additional refactoring, though the layered design mitigates this risk.


### `ConsoleUI` Coupling

The presentation layer relies on a console-based UI with a menu loop.

**Trade-offs / Debt:**

- Some logic for input validation and menu management is tightly coupled to console interaction, which may complicate future GUI or web-based implementations.

- Expanding menu options could introduce additional boilerplate, making the UI code harder to maintain.


### Strategy Pattern Registration by ID

Impact calculation strategies are selected using integer IDs passed to `ApplicationService.createImpactCalculationStrategy()`.

**Trade-offs / Debt:**

- A more scalable solution would be a registry or factory pattern with meaningful identifiers, but that was deemed outside the scope of this project.


### Layered Architecture Overhead

Adopting a strict three-layer architecture provides separation of concerns and clear responsibilities.

**Trade-offs / Debt:**

- For a small console application, the extra layers introduce additional boilerplate and indirection which complicates the overall codebase.

- Some classes may feel lightweight or thin, but this was a conscious decision to support future maintainability and extensibility.


Each of these technical debts was weighed against the benefits of clarity, testability, and extensibility from a real-world development perspective. We consciously accepted these debts to focus on object-oriented design principles, knowing that addressing them fully would be a natural next step in scaling the system for real-world usage.
