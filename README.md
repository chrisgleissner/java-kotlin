# java-kotlin

[![build](https://github.com/chrisgleissner/java-kotlin/actions/workflows/build.yaml/badge.svg)](https://github.com/chrisgleissner/java-kotlin/actions)
[![Coverage Status](https://coveralls.io/repos/github/chrisgleissner/java-kotlin/badge.svg?branch=main)](https://coveralls.io/github/chrisgleissner/java-kotlin?branch=main)

This repo contains equivalent code samples to compare [Lombok-ed Java 21](https://projectlombok.org/) with [Kotlin](https://kotlinlang.org/). It examines whether using Kotlin has benefits in terms of code maintainability and how easy it is to leverage these for a Java developer.

## Kotlin Benefits

- Kotlin is designed as a language that is easy to learn for a Java developer.
- It provides for full interoperability with Java, i.e. Kotlin code can call Java code and vice versa.
- It is not necessary to convert an entire code base from Java to Kotlin in order to benefit from its features.
- Common pitfalls in Java code are avoided or reduced, e.g. unexpected `NullPointerException`s and the cognitive complexity that comes with mutable code.
- The features described on this page can be leveraged by a Java developer after about 4 hours of study, reading through the references provided.

### Industry Support

- Introduced in 2011 and open sourced under the Apache 2 License, Kotlin is managed by the [Kotlin Foundation](https://kotlinfoundation.org/), a group created by JetBrains and Google, that is tasked with advancing and continuing development of the language.
- According to the Kotlin Foundation, it has 710k active developers, is used in 1.2 million GitHub repos, and 32 of the 100 top-ranking universities teach Kotlin.
- The [source code](https://github.com/JetBrains/kotlin) of Kotlin is hosted on GitHub and has seen 226 releases since 2013, most recently version 2.0.0.
- [Google](https://developer.android.com/kotlin) selected Kotlin in 2019 as its preferred language for Android development.
- [Spring Boot](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-kotlin.html) provides first-class support for Kotlin and shows all code examples both in Java and Kotlin.
- [Gradle](https://docs.gradle.org/current/userguide/kotlin_dsl.html), one of the most popular build systems for Java, chose Kotlin over Groovy due to its strong typing and ease of creating DSLs (domain-specific languages).
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) is a new initiative and supports the development of cross-platform projects, both on server-side and client-side (Android, iOS, Desktop, Web). Thus, language skills in Kotlin are transferable across different domains.

### Comparison to Lombok

Java projects typically rely on [Lombok](https://projectlombok.org/) in order to gain some of the benefits that Kotlin provides:

- Lombok is a 3rd party library and, though unlikely, some of its features may fall into disrepair in future Java versions as it relies on the `javac` compiler.
- Lombok relies on annotations, but as it does not support [meta-annotations](https://github.com/projectlombok/lombok/issues/2294), this can result in a large number of annotations cluttering the code base and
  reducing maintainability.
- Similar to Lombok, Kotlin can be viewed as a tool to simplify Java code where beneficial in order to improve code maintainability, but with an improved upgrade path as Kotlin does not rely on the `javac` compiler.

## Useful Kotlin Features

Kotlin provides a number of useful features for a Java developer.

The following features are easy to learn. They result in code that is easier to read and maintain whilst avoiding common Java pitfalls:

| Feature                                                                       | Feature ID | Description                                                                                                                                                                                                           | Java Alternative                               | Assessment                                                                                                                                               | 
|-------------------------------------------------------------------------------|------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Immutability](https://kotlinlang.org/docs/basic-syntax.html#variables)       | immutable  | Variables can be defined as `val` (immutable) and `var` (mutable). Parameters are always immutable.                                                                                                                   | Lombok `val` and Java `var`                    | Java does not support `val` for method parameters nor for instance/class fields, resulting in more verbose code by using `final $type` instead of `val`. |
| [Data classes](https://kotlinlang.org/docs/data-classes.html)                 | data-class | Data holder classes with default values.                                                                                                                                                                              | Lombok `@Builder` and Java `record`.           | Java is more verbose due to need for calling `build` method and the lack of default values in `record`s.                                                 |
| [Null-safe declarations](https://kotlinlang.org/docs/null-safety.html)        | null-safe  | Explicit type declaration as non-nullable (default) or nullable, using the `?` null-safety operator, allowing for compile-time null safety.                                                                           | Lombok `NonNull` and Java `Optional`.          | Java has no compile-time null-safety and is more verbose.                                                                                                |
| [Null-safe calls](https://kotlinlang.org/docs/null-safety.html)               | null-safe  | Access nullable fields via the [?.](https://kotlinlang.org/docs/null-safety.html#safe-calls) operator, defining fallbacks via the [?:](https://kotlinlang.org/docs/null-safety.html#elvis-operator) "Elvis" operator. | `if (a == null)` checks or `Optional` mapping. | Java is more verbose.                                                                                                                                    | 
| [String templates](https://kotlinlang.org/docs/strings.html#string-templates) | string     | Reference variables directly from strings.                                                                                                                                                                            | `String.formatted` method.                     | Java is harder to read, especially for many variables.                                                                                                   |
| [Scope functions](https://kotlinlang.org/docs/scope-functions.html)           | scope      | Allows for fluent code via method chaining, e.g. to log result as side effect of returning it.                                                                                                                        | Declare local variable, then access.           | Java is more verbose and adds increased cognitive load due to "single use" variables.                                                                    |

## Code Samples

For each code sample, the following is provided:

- Java prod code
- Kotlin prod code
- Java and Kotlin tests, each testing both Java and Kotlin prod code

Each row mentions the Kotlin features examined by the sample.

| Sample                                                                | Description                                   | immutable | data-class | null-safe | string | scope | Java                                                                   | Kotlin                                                                   | Java Test                                                                       | Kotlin Test                                                                       |
|-----------------------------------------------------------------------|-----------------------------------------------|-----------|------------|-----------|--------|-------|------------------------------------------------------------------------|--------------------------------------------------------------------------|---------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| [DtoSample](./src/main/java/uk/gleissner/javakotlin/dto/DtoSample.kt) | Build immutable DTO, convert to JSON, and log | ✓         | ✓          | ✓         | ✓      | ✓     | [Java](./src/main/java/uk/gleissner/javakotlin/dto/JavaDtoSample.java) | [Kotlin](./src/main/java/uk/gleissner/javakotlin/dto/KotlinDtoSample.kt) | [Java Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleJavaTest.java) | [Kotlin Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleKotlinTest.kt) |

