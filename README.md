# BIG COMPANY – Organizational Analysis

Java-based application that analyzes organizational structure by evaluating manager compensation and reporting hierarchy using employee data from a CSV file.

## Features
```text
1. Detect managers who earn:
   - Less than 20% above average salary of direct subordinates
   - More than 50% above average salary of direct subordinates
2. Detect employees whose reporting line to the CEO is longer than allowed (more than 4 managers).
```
## Assumptions
```text
- Exactly one CEO (no managerId)
- Valid manager relationships (no cycles)
- Invalid or incomplete rows (missing id or salary) are skipped during parsing
- Salary is a positive number
- CSV file is well-formed and comma-separated
- managerId is empty for the CEO.
- Employee id's are unique
```
## Tech Stack
```text
- Java SE 17
- Maven
- JUnit 5
```
## Project structure
```text
src/
├── main/
│   └── java/com/bigcompany/employeeanalyzer/
│       ├── model/
│       ├── service/
│       ├── reader/
│       ├── printer/
│       └── main/
│           └── Application.java
└── test/
    └── java/com/bigcompany/employeeanalyzer/
        └── EmployeeAnalyzerTest.java
```
## CLI Build & Run Steps
```text
mvn clean compile
mvn clean test
mvn clean install
mvn package

java -cp target/employee-analyzer-1.0-SNAPSHOT.jar com.bigcompany.employeeanalyzer.main.Application
```
## Sample Output
```text
Case 1: With Violations

************************ ORGANIZATION ANALYSIS REPORT ************************

UNDERPAID MANAGERS:
- Employee 305 (David Clark) is underpaid by 6800.00
- Employee 306 (Emma Stone) is underpaid by 6800.00
- Employee 307 (Chris Evans) is underpaid by 6800.00
- Employee 308 (Natalie Portman) is underpaid by 6800.00
- Employee 123 (Joe Doe) is underpaid by 19200.00
- Employee 124 (Martin Chekov) is underpaid by 15000.00

OVERPAID MANAGERS:
- Employee 500 (Tom Harris) is overpaid by 40000.00

EMPLOYEES WITH TOO LONG REPORTING LINE:
- Employee 308 (Natalie Portman) exceeds by 1 level
- Employee 309 (Natalie Kat) exceeds by 2 levels

Case 2: No Violations

************************ ORGANIZATION ANALYSIS REPORT ************************

UNDERPAID MANAGERS:
- None

OVERPAID MANAGERS:
- None

EMPLOYEES WITH TOO LONG REPORTING LINE:
- None
```
