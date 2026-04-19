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
- The system should ensure data accuracy for impact calculations (reliability).
- The system should be scalable to support many users (scalability).

## System Boundary

### Inside

- Product management
- Impact calculation
- Console UI
- Build tools (Make/CMake/gradle/maven)

### Outside

- GUI frameworks
- Payment systems
- Locale adaptation


## Domain Concept Identification

### Nouns (concepts)
- Product &mdash; `Entity`
    - Represents a single unit of the product entity in the system. It belongs to one product category and has a list of materials it contains.
- Material &mdash; `Entity`
    - Represents a single unit of the material that is used by products in the system. It provides the environmental impact value, and may be referenced by multiple products.
- ProductCategory &mdash; `Value`
    - Represents a single value holding the category of the product in the system. Primarily used to identify different types of products.
- RecyclingCategory &mdash; `Value`
    - Represents a single value holding the recycling category of the material in the system. Primarily used to identify different types of materials.
- RecyclingGuidance &mdash; `Service`
    - Represents instructions that need to be made to properly recycle the material. It knows the category of the material.
- RecyclingService &mdash; `Service`
    - Represents a service that recycles the materials. It must know the recycling instructions as well as the category of the material.
- ImpactReport &mdash; `Value`
    - Represents a single value holding the total environmental impact of the product.
- ImpactCalculationStrategy &mdash; `Service`
    - Represents a service that provides with an implementation on how to calculate the total environmental impact of one product. It must know the list of materials.
- StoragePool &mdash; `Entity`
    - Represents a single unit of the storage for core components of the system and provides a safe access to the presentation layer. Holds all products and material with ability to manage them.
- ApplicationService &mdash; `Service`
    - Represents a service that is designed to be a middleware between presentation layer and application layer (being in itself in application layer).
- ConsoleUI &mdash; `Entity`
    - Represents a single unit in the system that is used as a presentation layer for the project that handles all I/O. Uses storage pool(s) to manage the state of the application via a pre-defined interface.

### Verbs (responsibilities)
- Contains (product contains materials)
- Calculates (product calculates the environmental impact)
- Update (products)
- Register (products, materials, guidance, category, recycling strategy)
- Display (report, information about products/materials, etc.)

## CRC cards

<!--
CRC card template (HTML):

Preface:

tr = table row ; td = table data (cell)
colspan="{amount}" specifies how many columns does 1 cell occupies horizontally
[Type of the concept]: one of: Entyty | Value | Service
[Name of the concept]: name of the concept (e.g. Product, Material, ImpactReport)
id="[Type of the concept]-[Name of the concept]" - gives unique identifier to reference this specific concept in the document
No blank lines between tags <tr> and <td>!!!

Actual template:

<table>
    <tr><td colspan="2" id="[Type of the concept]-[Name of the concept]">[Type of the concept]: [Name of the concept]</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Responsibility 1</td>
        <td>Collaborators list 1</td>
    </tr>
    <tr>
        <td>Responsibility 2</td>
        <td>Collaborators list 2</td>
    </tr>
    <tr>
        <td>Responsibility 3</td>
        <td>Collaborators list 3</td>
    </tr>
</table>

NOTE:
    GitHub may not properly apply the styles due to aggressive sanitization of the markdown files.
    Tables without styles will stil be readable, but with less styling.

-->

**CRC** - Class Responsibility Collaboration

<table>
    <tr><td colspan="2" id="entity-Product">Entity: Product</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Knows it's Name, category, and lifespan</td>
        <td><a href="#value-ProductionCategory">ProductCategory</a> (owns)</td>
    </tr>
    <tr>
        <td>Holds it's list of materials (non empty list)</td>
        <td><a href="#entity-Material">Material</a> (owns)</td>
    </tr>
    <tr>
        <td>Determine it's computed environmental impact</td>
        <td>
            <a href="#entity-Material">Material</a> (uses)<br>
            <a href="#service-ImpactCalculationStrategy">ImpactCalculationStrategy</a> (uses)
        </td>
    </tr>
    <tr>
        <td>Provide recycling guidance</td>
        <td><a href="#service-RecyclingGuidance">RecyclingGuidance</a> (owns)</td>
    </tr>
    <tr>
        <td>Provide API for user interation</td>
        <td><a href="#entity-StoragePool">StoragePool</a> (owns)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="entity-Material">Entity: Material</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Knows it's name and impact value</td>
        <td><a href="#value-RecyclingCategory">RecyclingCategory</a> (depends on)</td>
    </tr>
    <tr>
        <td>Knowing recycling category and guidance</td>
        <td>
            <a href="#entity-Product">Product</a> (referenced by)<br>
            <a href="#value-RecyclingCategory">RecyclingCategory</a> (uses)<br>
            <a href="#service-RecyclingGuidance">RecyclingGuidance</a> (owns)
        </td>
    </tr>
    <tr>
        <td>Be reusable across products</td>
        <td>
            <a href="#value-RecyclingCategory">RecyclingCategory</a>
            (used by <a href="#entity-Product">Product</a>)<br>
            <a href="#entity-Product">Product</a> (referenced by)
        </td>
    </tr>
    <tr>
        <td>Provide API for user interation</td>
        <td><a href="#entity-StoragePool">StoragePool</a> (owns)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="service-ImpactCalculationStrategy">Service: ImpactCalculationStrategy</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Provide strategy how to calculate environmental impact based of materials</td>
        <td>
            <a href="#entity-Product">Product</a> (referenced by)<br>
            <a href="#entity-Material">Material</a> (uses)<br>
            <a href="#value-RecyclingCategory">RecyclingCategory</a> (uses)<br>
            <a href="#value-RecyclingGuidance">RecyclingGuidance</a> (uses)
        </td>
    </tr>
    <tr>
        <td>Different strategies must me interchangable without modifying the product</td>
        <td><a href="#entity-Product">Product</a> (referenced by)</td>
    </tr>
    <tr>
        <td>Compute an environmental impact based of materials and their configuratuion</td>
        <td><a href="#entity-Material">Material</a> (uses)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="value-ProductCategory">Value: ProductCategory</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Holds the category of the product</td>
        <td><a href="#entity-Product">Product</a> (referenced by)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="value-RecyclingCategory">Value: RecyclingCategory</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Holds the recycling category of the material</td>
        <td>
            <a href="#entity-Material">Material</a> (referenced by)<br>
            <a href="#service-ImpactCalculationStrategy">ImpactCalculationStrategy</a> (used by)
        </td>
    </tr>
    <tr>
        <td>Be reusable across multiple products that reference the same material</td>
        <td><a href="#entity-Material">Material</a> (referenced by)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="service-RecyclingGuidance">Service: RecyclingGuidance</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Define a recycling guidance for the material</td>
        <td><a href="#entity-Material">Material</a> (referenced by)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="service-RecyclingService">Service: RecyclingService</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Provide a method for recycling guidance to recycle the material</td>
        <td><a href="#value-RecyclingGuidance">RecyclingGuidance</a> (referenced by)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="value-ImpactReport">Value: ImpactReport</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Provide a concise way of representing the environmental impact</td>
        <td><a href="#entity-Product">Product</a> (produced by)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="entity-StoragePool">Entity: StoragePool</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Hold all products and materials in the system (in specific scope)</td>
        <td>
            <a href="#entity-Product">Product</a> (owns)<br>
            <a href="#entity-Material">Material</a> (owns)
        </td>
    </tr>
    <tr>
        <td>Manage all products and materials in the system (in specific scope)</td>
        <td>
            <a href="#entity-Product">Product</a> (owns)<br>
            <a href="#entity-Material">Material</a> (owns)
        </td>
    </tr>
    <tr>
        <td>Provide a safe interface to use on the presentation layer</td>
        <td><a href="#entity-ConsoleUI">ConsoleUI</a> (referenced by)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="entity-ConsoleUI">Entity: ConsoleUI</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>I/O part of the system</td>
        <td><a href="#entity-StoragePool">StoragePool</a> (provides data to show)</td>
    </tr>
    <tr>
        <td>Communicate through a defined interface with the main application to send and retrieve results</td>
        <td><a href="#service-ApplicationService">ApplicationService</a> (uses)</td>
    </tr>
</table>

<table>
    <tr><td colspan="2" id="service-ApplicationService">Service: ApplicationService</td></tr>
    <tr><td>Responsibilities</td><td>Collaborators</td></tr>
    <tr>
        <td>Handle requests from presentation API</td>
        <td><a href="#entity-ConsoleUI">ConsoleUI</a> (used by)</td>
    </tr>
</table>
