# java-kotlin

[![build](https://github.com/chrisgleissner/java-kotlin/actions/workflows/build.yaml/badge.svg)](https://github.com/chrisgleissner/java-kotlin/actions)
[![Coverage Status](https://coveralls.io/repos/github/chrisgleissner/java-kotlin/badge.svg?branch=main)](https://coveralls.io/github/chrisgleissner/java-kotlin?branch=main)

This repo contains equivalent code samples to compare [Lombok-ed Java 21](https://projectlombok.org/) with [Kotlin](https://kotlinlang.org/). It examines whether using Kotlin has benefits in terms of code maintainability and how easy it is to leverage these for a Java developer.

## Kotlin Benefits

- Kotlin is designed as a language that is easy to learn for a Java developer.
- It was designed with [full interoperability with Java](https://kotlinlang.org/docs/java-interop.html) in mind, i.e. Kotlin code can call Java code and vice versa.
- It is not necessary to convert an entire code base from Java to Kotlin in order to benefit from its features.
- Common pitfalls in Java code are avoided or reduced, such as unexpected `NullPointerException`s or the cognitive complexity that comes with mutable code.
- The features described on this page can be leveraged by a Java developer after about 4 hours of study, reading through the references provided.

## Kotlin Industry Support

- Introduced in 2011 and open sourced under the Apache 2 License, Kotlin is managed by the [Kotlin Foundation](https://kotlinfoundation.org/):
    - The foundation was created by JetBrains and Google. It advances the development of the language.
    - According to the foundation, Kotlin has 710k active developers and is used in 1.2 million GitHub repos. 32 of the 100 top-ranking universities teach Kotlin.
- [StackOverflow Trends](https://insights.stackoverflow.com/trends?tags=java%2Ckotlin) reports a steady upward trend of Kotlin since 2016, contrasting with a downward trend of Java over the same period. Now, Kotlin receives 30% as many StackOverflow postings as Java.
- [Google](https://developer.android.com/kotlin) selected Kotlin in 2019 as its preferred language for Android development.
- [Spring Boot](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-kotlin.html) provides first-class support for Kotlin and shows all [code examples](https://docs.spring.io/spring-boot/reference/features/spring-application.html) both in Java and Kotlin.
- [Gradle](https://docs.gradle.org/current/userguide/kotlin_dsl.html), one of the most popular build systems for Java, chose Kotlin over Groovy due to its strong typing and ease of creating DSLs (domain-specific languages).
- The [source code](https://github.com/JetBrains/kotlin) of Kotlin is hosted on GitHub and has seen 226 releases since 2013, most recently version 2.0.0 which saw up to 94%
  in [compilation performance](https://blog.jetbrains.com/kotlin/2024/04/k2-compiler-performance-benchmarks-and-how-to-measure-them-on-your-projects/) gains.
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) is a new initiative and supports the development of cross-platform projects, both on server-side and client-side (Android, iOS, Desktop, Web). Thus, language skills in Kotlin are transferable across different domains.

## Resources

A short selection of "Getting Started" guides:

- [Syntax](https://kotlinlang.org/docs/basic-syntax.html)
- [Learn Kotlin By Examples](https://play.kotlinlang.org/byExample/)
- [Kotlin Cheat Sheet](https://devhints.io/kotlin)
- [Java to Kotlin Cheat Sheet](https://github.com/amitshekhariitbhu/from-java-to-kotlin)
- [Kotlin Code Samples](https://learnxinyminutes.com/docs/kotlin/)

## Kotlin vs Lombok

Modern Java projects typically rely on [Project Lombok](https://projectlombok.org/) in order to gain some of the code maintainability benefits that Kotlin provides. Thus, one may wonder whether the combination of Java 21 and Lombok affords the same benefits as Java 21 and Kotlin. Let's compare them.

### Lombok

- Lombok is a 3rd party library. As it relies on the `javac` compiler, some of its features may fall into disrepair in future Java versions. It may also not support (LTS) Java versions. Some examples:
    - The use of Lombok's `val` resulted in very slow compile times when the Java 11 LTS version was officially released. This [bug](https://github.com/projectlombok/lombok/issues/2131) lasted for months until a fix was provided. Until that happened, we were forced to
      replace all `val` references with `var` since the compilation times had increased from 10s to 2 minutes.
    - The Java code samples in this project won't compile on Java 22 due to `post-compiler 'lombok.bytecode.SneakyThrowsRemover' caused an exception: java.lang.IllegalArgumentException: Unsupported class file major version 66` since at this time,
      only [initial Java 22 support](https://projectlombok.org/changelog) has been added.
- Lombok relies on annotations, but as it does not support [composable annotations](https://github.com/projectlombok/lombok/issues/2294), this can result in a large number of annotations cluttering the code base and
  reducing maintainability.
- Lombok is an open source project which is very complex and largely driven forward by a [single developer](https://github.com/projectlombok/lombok/graphs/contributors).

### Kotlin

- Kotlin is similar to Java and co-operates well with the overall ecosystem:
    - The learning curve is small; certainly not bigger than comprehending Lombok and its [idiosyncrasies](https://projectlombok.org/features/experimental/SuperBuilder).
    - It can be viewed as a Java developer productivity tool to [enrich](https://kotlinlang.org/docs/mixing-java-kotlin-intellij.html) Java-based projects. Some Java code can be converted to Kotlin in order to improve code maintainability, whilst leaving the rest as Java.
    - JetBrains IntelliJ IDEA provides [first-class support](https://www.jetbrains.com/help/idea/get-started-with-kotlin.html) for Kotlin and offers a shortcut to convert Java to Kotlin code.
- Unlike Lombok, it does not rely on the `javac` compiler, thus simplifying the upgrade to new Java versions.
- It does not rely on annotations to extend Java features, thus improving readability.
- The language has a large number of [active contributors](https://github.com/JetBrains/kotlin/graphs/contributors) and JetBrain's Kotlin team has [100+ engineers](https://kotlinlang.org/docs/faq.html#who-develops-kotlin). It is backed by both JetBrains and Google.

## Useful Kotlin Features

Kotlin provides a number of useful features for a Java developer which are easy to learn. Properly leveraged, they result in code that's easier
to read and maintain than the equivalent Java code.

| Feature                                                                                              | Feature ID | Description                                                                                                                                                                                                                                                                   | Kotlin Example                            | Java Alternative                                                                                        | Assessment                                                                                                    | 
|------------------------------------------------------------------------------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|---------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| [Data classes](https://kotlinlang.org/docs/data-classes.html)                                        | dat        | Data holder classes with default values.                                                                                                                                                                                                                                      | `data class Dept(val name: String)`       | Lombok `@Builder` and `@NonNull`, e.g. `@Builder record Dept(@NonNull String name) {}`)                 | Java is more verbose: `builder()/toBuilder()/build()` methods and no default values in `record`s.             |
| [Data access](https://kotlinlang.org/docs/classes.html)                                              | acc        | [Instantiate](https://kotlinlang.org/docs/classes.html#creating-instances-of-classes) without `new`, [access properties](https://kotlinlang.org/docs/properties.html#declaring-properties) without `get/set`, [compare](https://kotlinlang.org/docs/equality.html) with `==`. | `Dept("IT").name == "IT"`                 | `new Dept("IT").getName().equals("IT")`                                                                 | Java is more verbose and Lombok does not provide solutions.                                                   |
| [Exceptions](https://kotlinlang.org/docs/exceptions.html)                                            | exc        | Kotlin does not have checked exceptions since they have fallen [out of favour](https://kotlinlang.org/docs/exceptions.html#checked-exceptions) with many developers as they increase code clutter.                                                                            |                                           | `try / catch` exception and rethrow non-checked or Lombok's `SneakyThrows` (currently fails on Java 22) | Java is more verbose, requiring workarounds that introduce code clutter.                                      |
| [Extensions](https://kotlinlang.org/docs/extensions.html)                                            | ext        | Extend an existing Kotlin or Java API with new features, e.g. for a fluent API and improved readability.                                                                                                                                                                      | `entity.dto.json`                         | Nested methods in reverse processing order: `json(dto(entity))`                                         | Java is harder to read and creating a fluent API is not easy for 3rd party libraries.                         |
| [Immutability](https://kotlinlang.org/docs/basic-syntax.html#variables)                              | imm        | Variables can be defined as `val` (immutable) and `var` (mutable). Parameters are always immutable.                                                                                                                                                                           |                                           | Lombok `val` and Java `var`, but not supported for  method parameters and instance/class fields.        | Java is more verbose, requiring workarounds using the `final` keyword.                                        |
| [Null-safe declarations](https://kotlinlang.org/docs/null-safety.html)                               | nul        | Explicit type declaration as non-nullable (default) or nullable, using the `?` null-safety operator, allowing for compile-time null safety.                                                                                                                                   | `val s: String?`                          | Lombok `@NonNull`, Spring `@Nullable` or Java `Optional`, e.g. `@Nullable final String s`               | Java has no compile-time null-safety and is more verbose.                                                     |
| [Null-safe calls](https://kotlinlang.org/docs/null-safety.html)                                      | nul        | Access nullable fields via the [?.](https://kotlinlang.org/docs/null-safety.html#safe-calls) operator, defining fallbacks via the [?:](https://kotlinlang.org/docs/null-safety.html#elvis-operator) "Elvis" operator.                                                         | `a?.bar`                                  | Null check like `a == null ? null : a.bar` or `Optional` mapping                                        | Java is more verbose.                                                                                         | 
| [Scope functions](https://kotlinlang.org/docs/scope-functions.html)                                  | scp        | Allows for fluent code via method chaining and `it/this` references, e.g. to log result as side effect of returning it.                                                                                                                                                       | `return map(a).also { log { "res=$it" }}` | Declare local variable, then access: `val res = map(a); log("res={}", res); return res;`                | Java is more verbose and adds increased cognitive load due to "single use" variables instead of a fluent API. |
| [Spaces in Test Methods](https://kotlinlang.org/docs/coding-conventions.html#names-for-test-methods) | spc        | Long test method names can contain spaces. They are thus more readable and match [Cucumber](https://cucumber.io/docs/cucumber/step-definitions/?lang=java) BDD step annotations.                                                                                              | ``fun `Given foo When bar Then baz`() {`` | `void Given_foo_When_bar_Then_baz() {`                                                                  | Java is slightly harder to read due to using underscores instead of spaces.                                   |
| [String templates](https://kotlinlang.org/docs/strings.html#string-templates)                        | str        | Reference variables directly from strings.                                                                                                                                                                                                                                    | `"foo=$foo, bar=$bar"`                    | Separate placeholders and variables: `"foo=%s, bar=%s".formatted(foo, bar)`                             | Java is harder to read, especially when using many variables.                                                 |
| [Nested functions](https://kotlinlang.org/docs/functions.html#function-scope)                        | fun        | Nested private helper functions to improve encapsulation.                                                                                                                                                                                                                     | `fun foo():Int { fun bar():Int {...}}`    | Could use inner classes or factor out into other classes, but less flexible.                            | Splitting methods quickly results in pollution of class namespace.                                            | 
| [One-line functions](https://kotlinlang.org/docs/functions.html#function-usage)                      | fun        | One-line function without result type, `return` statement and braces to reduce ceremony and preserve screen real-estate.                                                                                                                                                      | `fun add(a: Int, b: Int) = a + b`         | `int add(int a, int b) { return a + b; }`, typically formatted as 3 lines.                              | Java wastes vertical screen real estate, leading to more scrolling.                                           | 

## Kotlin Code Samples

This chapter compares equivalent code in Java and Kotlin.

Description of table columns:

- **Sample**: Interface followed by implementations for Java and Kotlin, then followed by tests using Java and Kotlin. Both tests verify both implementations.
- **Description**: High-level overview of sample use case.
- **dat, acc, ...**: IDs of Kotlin features examined by the sample; see table above.

| Sample                                                                                                                                                                                                                                                                                                                                                                                        | Description                                   | dat | acc | exc | ext | imm | nul | scp | spa | str | fun |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|
| [DtoSample](./src/main/java/uk/gleissner/javakotlin/dto/DtoSample.kt): [Java](./src/main/java/uk/gleissner/javakotlin/dto/JavaDtoSample.java) - [Kotlin](./src/main/java/uk/gleissner/javakotlin/dto/KotlinDtoSample.kt) - [Java Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleJavaTest.java) -[Kotlin Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleKotlinTest.kt) | Build immutable DTO, convert to JSON, and log | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   | ✓   |


