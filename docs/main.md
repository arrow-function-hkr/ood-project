<!--
    links to the sections should match the section name with hyphens instead of spaces,
    in URL-compatible format (e.g. '/' -> '%2F', '#' -> '%23')
-->

# Documentation for the project


## Types of commits/branch names

<!-- hyperlink: [`change-type`](#types-of-commits%2Fbranch-names) -->

All general-purpose types of commit messages and branch names
(will be aliased in this document as: [`change-type`](#types-of-commits%2Fbranch-names))

- `build`    : adjustment of build tools or external dependencies
- `chore`    : generic maintenance (i.e. no product functionality change)
- `ci`       : modification of Continuous Integration (CI) configurations/scripts
- `docs`     : changes to the documentation
- `feat`     : new feature for the end user (i.e. not a new feature for build script)
- `fix`      : bug fix for the end user (i.e. not a fix to a build script)
- `perf`     : source code adjustment that improves performance
- `refactor` : refactoring product code (neither fixes a bug nor adds a feature)
- `revert`   : undo previous commits
- `style`    : code formatting or code style adjustment (i.e. no product functionality change)
- `test`     : implementation of missing or adjustment of existing test (i.e. no product functionality change)

### Branch naming

Branch names follows the following naming convention:

[`change-type`](#types-of-commits%2Fbranch-names)/\{scope\} or [`change-type`](#types-of-commits%2Fbranch-names)/\{change-action\}/\{scope\}

The `scope` part of the branch name should refer to the short description of the scope to which the change was applied.

The `change-action` part of the branch name should refer to the one word that describes the action performed in the [`change-type`](#types-of-commits%2Fbranch-names). Examples (not limited to): `add`, `remove`, `fix`, `refactor`

Examples:

_Add icons support (feature) regarding the button_: `feat/add/button-icon-support`

_Make adjustments (fix) to the icon link in form of reducing space between them_: `fix/spacing-icon-link`

_Implementation (feature) of the new navigation that breaks the previous state of the project (in some amount)_: `feat/navigation`

_Add new unit test_: `test/add/add-user`

_Change CI pipeline_: `ci/refactor/credentials-change`


### Commit messages

Following this convention:
```
<type>[ (<scope>) ][!]: <subject>

[body]

[footer]
```
where:
- `type` (required) &mdash; type of the change ([`change-type`](#types-of-commits%2Fbranch-names))
- `scope` (optional) &mdash; the scope to which the change applies. If specified, describes the shortest field to which it is applied
- `!` (optional) &mdash; exclamation mark to indicate a breaking change (not backwards compatible, i.e. new version of the project may not work with previous code)
- `subject` (required):
    - imperative, present tense (i.e. "change" instead of "changed" or "changes")
    - short description (soft limit ~50 characters) of the changes
    - first letter is lowercased
    - skip the full stop (`.`)
- `body` (optional): the content of the changes in full form
- `footer` (optional): short footer sentence

> [!NOTE]
> Changes to the UML diagram files (PlantUML files with `.puml` extension) are considered to be of type `docs` (documentation) changes, since UML describes the project structure, and not the implementation.

Examples:

_Add icons support (feature) regarding the button_
```gitcommit
feat(button): implement support for icons

Fixes issue #123
```

_Make adjustments (fix) to the icon link in form of reducing space between them_
```gitcommit
fix(icon-link): reduce spacing between link label and icon
```

_Implementation (feature) of the new navigation that breaks the previous state of the project (in some amount)_
```gitcommit
feat(navigation)!: implement new navigation

BREAKING CHANGE: The props of the new navigation are incompatible with the previous navigation
```

_Add new unit test_
```gitcommit
test(add-user): add 3 unit tests for adding the user
```

_Change CI pipeline_
```gitcommit
ci(credentials): change credentials for the deployment phase
```
