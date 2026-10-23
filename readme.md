# Rappi technical test

[![CI](https://github.com/grug-dev/rappi-test/actions/workflows/ci.yml/badge.svg)](https://github.com/grug-dev/rappi-test/actions/workflows/ci.yml)

This repo holds my answers to a technical test. It has three parts.

| Part | What it is |
|------|------------|
| [Coding challenge](coding-challenge/readme.md) | A web app that solves the HackerRank [Cube Summation](https://www.hackerrank.com/challenges/cube-summation/problem) problem. You upload a text file and get the results. |
| [Code refactoring](code_refactoring/readme.md) | A refactor of a given piece of Java code, with notes on the bad practices found (in Spanish). |
| [Questions](preguntas/readme.md) | Answers to the theory questions (in Spanish). |

## Coding challenge in short

- `coding-challenge/cube-summation` - REST API with Spring Boot 1.5.9 and Java 8.
  `POST /cube/upload` takes the file and runs each `UPDATE` and `QUERY` operation.
  Each operation is a Strategy class.
- `coding-challenge/cube-front` - Angular 5 source of the front.
  A built copy is in `cube-summation/src/main/webapp`.

## How to run

You need Java 8 and Maven 3.

Run the tests:

```bash
cd coding-challenge/cube-summation
mvn test
```

Start the API (port 9999):

```bash
mvn spring-boot:run
curl -F file=@src/test/resources/sample-input.txt http://localhost:9999/cube/upload
```

Start the API together with the bundled front. The front expects the `/CS` path:

```bash
mvn package
java -jar target/CS.war --server.context-path=/CS
```

Then open http://localhost:9999/CS/.

## CI

GitHub Actions runs `mvn test` for `cube-summation` with Java 8 on every push and pull request.
See [.github/workflows/ci.yml](.github/workflows/ci.yml).
