# Sustainable Product and Recycling Management System ([SDG 12](https://globalgoals.org/goals/12-responsible-consumption-and-production/))

This Java console application is a **menu-driven system for managing products and recyclable materials**, designed with **object-oriented principles** and **test-driven development** practices in mind. It allows users to:

- Define and manage products and reusable material types
- Calculate environmental impact using multiple interchangeable strategies
- Provide recycling guidance for single- and mixed-material products
- Maintain a clean separation between business logic and user interaction

The application follows the defined [UML class diagram](docs/UML.puml) that defines the relations between classes.

The application emphasizes **maintainable, testable, and well-structured code**, demonstrating clear responsibility distribution and professional development practices.

The description of the project can be found in [docs/project_description.md](docs/project_description.md) file.

The distributed roles across group members can be found in [docs/roles.md](docs/roles.md) file.

This project is designed and tested with Java 25. There is no 100% guarantee that the project will work on older versions of Java but it most litkely will, since this project uses pretty stable API from Java 17 and above.

---

## Run the project

To run the project, either use the pre-configured build tool, Make:
```sh
make
```
with optional targets:
  - `compile`
  - `execute`

and optional patameters:
  - `verbose=<boolean>`: show more information on how the compilation and execution is done

or compile the source code from the `./src/` directory manually, excluding `./src/test/` directory, which contains tests. Entry point to the project is `./src/Main.java`


### Tests 

To run unit tests, either use the pre-configured build tool, Make:
```sh
make test
```
with optional parameters:
  - `silent=<boolean>`: suppresses the logging of each testing step
  - `verbose=<boolean>`: shows extra information what make does under the hood
  - `method=<1|bitsadmin|2|powershell|3|curl>`: method for downloading dependency files on Windows (default: `bitsadmin`). If one method is slow or unavailable, try another one.

or compile the source test code from the `./src/test/` directory manually, including the JUnit framework jar dependency, which can be obtained by running the:
```sh
make get-junit
```
or by downloading the JUnit 5+ jar file from the maven repositories manually.

The 'all-in-one' tests target is `test-full` (which accepts the same parameters as the `test` target), which involves:
  - compilation of the source project code into bytecode (options from `compile` and `execute` targets apply)
  - compilation of the source test code into bytecode
  - execution of the tests

This target ensures that the tests always have the latest bytecode of both, the project and the tests, minimizing the riscs of testing the old code.

---

## License

This project is licensed under the 0BSD License - see the [LICENSE](LICENSE) file for details.  
