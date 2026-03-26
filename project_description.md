<style>
.red { color: red }
</style>

# Description of the project

## Functional Requirements

- The system shall create products with: name, category, estimated lifespan, and materials.
- The system shall list registered products.
- The system shall view detailed product information.
- The system shall define materials with: name, environmental impact value, and recycling instruction.
- The system shall allow users to register and manage products.
- The system shall track the lifecycle of a product (created, used, recycled).
- The system shall calculate environmental impact (e.g., carbon footprint).
- The system shall suggest recycling or reuse options for products.
- The system shall allow users to search and view product sustainability data.
- The system shall store recycling guidelines for different materials.

## Non-Functional Requirements

- The system should provide recycling guidance based on the product’s material composition.
- The system should be able to replace strategy without modifying the Product class (Strategy pattern required).
- The system should reuse materials across multiple products.
- The system should be easy to use (usability).
- The system should respond to queries within 2 seconds (performance).
- The system should ensure data accuracy for impact calculations (reliability).
- The system should be scalable to support many users (scalability).

## System Boundary

### Inside

- Product management
- Impact calculation

### Outside

- GUI frameworks
- Payment systems


## Domain Concept Identification

### Nouns (concepts)
- Product : `Entity`
- Material : `Entity`
- ProductCategory : `Value`
- RecyclingGuidance : `Service` (unsure)
- RecyclingService : `Service`
- ImpactReport : `Value`
- ImpactCalculationStrategy : `Service`

### Verbs (responsibilities)
- Contains (product contains materials)
- Calculates (product calculates the environmental impact)
- Update (products)
- Register (products, materials, guidance, category, recycling strategy)
- Display (report, information about products/materials, etc.)
