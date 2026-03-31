# Group roles

## Roles description

### <span id="role-system-architect">System Architect</span>

Responsible for designing the overall structure of the system (layers, classes, relationships) and ensuring that responsibilities are correctly distributed across the architecture


### <span id="role-domain-logic-developer">Domain Logic Developer</span>

Implements the core business logic (Product, Material, rules, calculations) while keeping it independent from UI and external layers


### <span id="role-strategy-pattern-developer">Strategy Pattern Developer</span>

Designs and implements the interchangeable calculation strategies and ensures the Strategy pattern is applied correctly without modifying existing classes


### <span id="role-application-layer-developer">Application Layer Developer</span>

Builds service/use-case classes that connect the UI with the domain logic and coordinate system operations without containing business rules


### <span id="role-console-ui-developer">Console UI Developer</span>

Implements the menu-driven interface and handles all input/output, ensuring no business logic is placed in the presentation layer


### <span id="role-testing-and-ci-engineer">Testing & CI Engineer</span>

Writes unit tests for core logic and sets up continuous integration to automatically build and verify the system on every update.


### <span id="role-documentation-and-git-manager">Documentation & Git Manager</span>

Maintains project documentation (README, UML, diagrams) and ensures proper Git workflow with clean commits and structured branches.


### <span id="role-concept-researcher-and-explainer">Concept Researcher & Explainer</span>

Finds and studies key concepts (architecture, design patterns, principles) and prepares clear, relevant explanations for the team. Also teaches the researched concepts to the team in simple terms and ensures everyone understands how they apply to the project


### <span id="role-design-validator">Design Validator</span>

Reviews the design and code to ensure principles like SRP, OCP, and proper layering are followed and identifies design weaknesses


### <span id="role-integration-coordinator">Integration Coordinator</span>

Ensures all parts of the system (UI, services, domain) connect correctly and that the overall structure remains consistent during development

<hr style="border-width: 1px; border-style: dashed;">

Total roles count: 10

<hr style="border-width: 1px">

## Roles assigned to group members

**Group leader**: Maksym Ignatiev 0006


### Per person


|             Person         |                                                                                                        Roles                                                                                                        |
|----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Boniface Mwangi Maina 0012 | <a href="#role-design-validator">Design Validator</a><br><a href="#role-domain-logic-developer">Domain Logic Developer</a><br><a href="#role-concept-researcher-and-explainer">Concept Researcher & Explainer</a>   |
| Maksym Ignatiev 0006       | <a href="#role-console-ui-developer">Console UI Developer</a><br><a href="#role-testing-and-ci-engineer">Testing & CI Engineer</a><br><a href="#role-documentation-and-git-manager">Documentation & Git Manager</a> |
| Shady Khalil 0016          | <a href="#role-integration-coordinator">Integration Coordinator</a><br><a href="#role-application-layer-developer">Application Layer Developer</a>                                                                  |
| Vladyslav Yanchuk 0001     | <a href="#role-system-architect">System Architect</a><br><a href="#role-strategy-pattern-developer">Strategy Pattern Developer</a>                                                                                  |

### Per role

|                                        Role                                         |           Person            |
|-------------------------------------------------------------------------------------|-----------------------------|
| <a href="#role-system-architect">System Architect</a>                               | Vladyslav Yanchuk 0001      |
| <a href="#role-domain-logic-developer">Domain Logic Developer</a>                   | Boniface Mwangi Maina 0012  |
| <a href="#role-strategy-pattern-developer">Strategy Pattern Developer</a>           | Vladyslav Yanchuk 0001      |
| <a href="#role-application-layer-developer">Application Layer Developer</a>         | Shady Khalil 0016           |
| <a href="#role-console-ui-developer">Console UI Developer</a>                       | Maksym Ignatiev 0006        |
| <a href="#role-testing-and-ci-engineer">Testing & CI Engineer</a>                   | Maksym Ignatiev 0006        |
| <a href="#role-documentation-and-git-manager">Documentation & Git Manager</a>       | Maksym Ignatiev 0006        |
| <a href="#role-concept-researcher-and-explainer">Concept Researcher & Explainer</a> | Boniface Mwangi Maina 0012  |
| <a href="#role-design-validator">Design Validator</a>                               | Boniface Mwangi Maina 0012  |
| <a href="#role-integration-coordinator">Integration Coordinator</a>                 | Shady Khalil 0016           |
