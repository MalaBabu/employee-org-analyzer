# BIG COMPANY – Organizational Analysis

This project analyzes the organizational structure of a company based on a CSV file of employees.

## Features

1. Detect managers who earn:
   - Less than 20% above average salary of direct subordinates
   - More than 50% above average salary of direct subordinates

2. Detect employees whose reporting line to the CEO is longer than allowed (more than 4 managers).

## Assumptions

- Exactly one CEO (no managerId)
- Valid manager relationships (no cycles)
- CSV contains up to 1000 employees 
- Invalid or incomplete rows (missing id or salary) are skipped during parsing
- CSV file is well-formed and comma-separated
- managerId is empty for the CEO.

## Tech Stack

- Java SE 17
- Maven
- JUnit 5

## Project structure

src/main/java/com/bigcompany/employeeanalyzer
    ├── model
    ├── service
    ├── reader

src/test/java/com/bigcompany/employeeanalyzer
    ├── EmployeeAnalyzerTest

## CLI Build & Run Steps

mvn clean compile
mvn clean test
mvn clean install
mvn package

java -cp target/employee-analyzer-1.0-SNAPSHOT.jar com.bigcompany.employeeanalyzer.main.Application


## Sample Output


************************ ORGANIZATION ANALYSIS REPORT ************************

UNDERPAID MANAGERS:
- Employee 305 (David Clark) is underpaid by 6800.00
- Employee 306 (Emma Stone) is underpaid by 6800.00
- Employee 307 (Chris Evans) is underpaid by 6800.00
- Employee 123 (Joe Doe) is underpaid by 19200.00
- Employee 124 (Martin Chekov) is underpaid by 15000.00

OVERPAID MANAGERS:
- Employee 500 (Tom Harris) is overpaid by 40000.00

EMPLOYEES WITH TOO LONG REPORTING LINE:
- Employee 307 (Chris Evans) exceeds by 1 level
- Employee 308 (Natalie Portman) exceeds by 2 levels




