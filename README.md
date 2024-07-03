# Java and Kotlin

[![build](https://github.com/chrisgleissner/java-kotlin/actions/workflows/build.yaml/badge.svg)](https://github.com/chrisgleissner/java-kotlin/actions)
![Coveralls](https://img.shields.io/coverallsCoverage/github/chrisgleissner/java-kotlin)

This repo contains equivalent code samples to compare [Lombok-ed Java 21](https://projectlombok.org/) with [Kotlin](https://kotlinlang.org/). It examines whether using Kotlin has benefits in terms of code maintainability and how easy it is to leverage these for a Java developer.

## Kotlin Benefits

- Kotlin is designed to be easy for a Java developer to learn.
- It was designed with [full interoperability with Java](https://kotlinlang.org/docs/java-interop.html) in mind, i.e., Kotlin code can call Java code and vice versa.
- It is not necessary to convert an entire codebase from Java to Kotlin in order to benefit from its features.
- Common pitfalls in Java code are avoided or reduced, such as unexpected `NullPointerException`s or the cognitive complexity that comes with mutable code.
- The features described on this page can be leveraged by a Java developer after about 4 hours of study, reading through the references provided.

## Industry Support

- Introduced in 2011 and open-sourced under the Apache 2 License, Kotlin is managed by the [Kotlin Foundation](https://kotlinfoundation.org/):
    - The foundation was created by JetBrains and Google. It advances the development of the language.
    - According to the foundation, Kotlin has 710k active developers and is used in 1.2 million GitHub repos. 32 of the top 100 universities teach Kotlin.
- [StackOverflow Trends](https://insights.stackoverflow.com/trends?tags=java%2Ckotlin) reports a steady upward trend of Kotlin since 2016, contrasting with a downward trend of Java over the same period. Now, Kotlin receives 30% as many StackOverflow postings as Java.
- [Google](https://developer.android.com/kotlin) selected Kotlin in 2019 as its preferred language for Android development.
- [Spring Boot](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-kotlin.html), the most popular framework for Java microservices, provides first-class support for Kotlin and shows
  all [code examples](https://docs.spring.io/spring-boot/reference/features/spring-application.html) in both Java and Kotlin.
- [Gradle](https://docs.gradle.org/current/userguide/kotlin_dsl.html), one of the most popular build systems for Java, chose Kotlin over Groovy due to its strong typing and ease of creating DSLs (domain-specific languages).
- The [source code](https://github.com/JetBrains/kotlin) of Kotlin is hosted on GitHub and has seen 226 releases since 2013, most recently version 2.0.0, which saw up to 94% improvements
  in [compilation performance](https://blog.jetbrains.com/kotlin/2024/04/k2-compiler-performance-benchmarks-and-how-to-measure-them-on-your-projects/).
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) is a new initiative and supports the development of cross-platform projects, both on the server-side and client-side (Android, iOS, Desktop, Web). Thus, language skills in Kotlin are transferable across different domains.

## Kotlin vs Lombok

Modern Java projects typically rely on [Project Lombok](https://projectlombok.org/) to gain some of the code maintainability benefits that Kotlin provides. Thus, one may wonder whether the combination of Java 21 and Lombok affords the same benefits as Java 21 and Kotlin. Let's compare them.

### Lombok

- Lombok is a 3rd party library. As it relies on the `javac` compiler, some of its features may fall into disrepair in future Java versions. It may also not support (LTS) Java versions. Some examples:
    - The use of Lombok's `val` resulted in very slow compile times when the Java 11 LTS version was officially released. This [bug](https://github.com/projectlombok/lombok/issues/2131) lasted for months until a fix was provided. Until that happened, we were forced to replace all `val` references
      with `var` since the compilation times had increased from 10s to 2 minutes.
    - The Java code samples in this project won't compile on Java 22 due to `post-compiler 'lombok.bytecode.SneakyThrowsRemover' caused an exception: java.lang.IllegalArgumentException: Unsupported class file major version 66` since at this time,
      only [initial Java 22 support](https://projectlombok.org/changelog) has been added.
- Lombok relies on annotations, but as it does not support [composable annotations](https://github.com/projectlombok/lombok/issues/2294), this can result in a large number of annotations cluttering the codebase and reducing maintainability.
- Lombok is an open-source project which is very complex and largely driven forward by a [single developer](https://github.com/projectlombok/lombok/graphs/contributors).

### Kotlin

- Kotlin is similar to Java and cooperates well with the overall ecosystem:
    - The learning curve is small; certainly not bigger than comprehending Lombok and its [idiosyncrasies](https://projectlombok.org/features/experimental/SuperBuilder).
    - It can be viewed as a Java developer productivity tool to [enrich](https://kotlinlang.org/docs/mixing-java-kotlin-intellij.html) Java-based projects. Some Java code can be converted to Kotlin to improve code maintainability while leaving the rest as Java.
    - JetBrains IntelliJ IDEA provides [first-class support](https://www.jetbrains.com/help/idea/get-started-with-kotlin.html) for Kotlin and offers a shortcut to convert Java to Kotlin code.
- Unlike Lombok, it does not rely on the `javac` compiler, thus simplifying the upgrade to new Java versions.
- It does not rely on annotations to extend Java features, thus improving readability.
- The language has a large number of [active contributors](https://github.com/JetBrains/kotlin/graphs/contributors), and JetBrain's Kotlin team has [100+ engineers](https://kotlinlang.org/docs/faq.html#who-develops-kotlin). It is backed by both JetBrains and Google.

## Resources

A short selection of "Getting Started" guides:

- [Syntax](https://kotlinlang.org/docs/basic-syntax.html)
- [Learn Kotlin By Examples](https://play.kotlinlang.org/byExample/)
- [Kotlin Cheat Sheet](https://devhints.io/kotlin)
- [Java to Kotlin Cheat Sheet](https://github.com/amitshekhariitbhu/from-java-to-kotlin)
- [Kotlin Code Samples](https://learnxinyminutes.com/docs/kotlin/)

## Useful Kotlin Features

Kotlin provides a number of useful features for a Java developer which are easy to learn. Properly leveraged, they result in code that's easier to read and maintain than the equivalent Java code.

### Data Classes

**Kotlin**

Data holder classes with default values, providing `getter/setter/toString/equals/hashCode` as well as `copy` methods:

```kotlin
data class Dept(val name: String = "IT", val staffCount: Int = 0)

val itDept = Dept()
val itDept2 = itDept.copy(staffCount = 1)
val hrDept = Dept(name = "HR")
```

**Java**

Java is more verbose and requires `builder/toBuilder/build` methods.

Additional downsides:

- If using Lombok's `@Value` annotation, then Lombok's `@Builder.Default` can be used for default values. However, this feature is not available of using Java `record`s instead.
- If starting out with Java `record`s and the for default values arises, then switching towards `@Value` requires refactoring since both approaches use different field declarations and different syntax for getter methods.

Java `record` approach which cannot uses defaults and exposes a `name()` method:

```java

@Builder(toBuilder = true)
record Dept(@NonNull String name, int staffCount) {
}

val itDept = Dept.builder().name("IT").build();
val itDeptName = itDept.name();
val itDept2 = itDept.toBuilder().staffCount(1).build();
val hrDept = Dept.builder().name("HR").build();
```

Lombok `@Value` approach which can use defaults and exposes a `getName()` method:

```java

@Value
@Builder(toBuilder = true)
class Dept {

    @NonNull
    @Builder.Default
    String name = "IT";

    int staffCount;
}

val itDept = Dept.builder().build();
val itDeptName = itDept.getName();
// ...as above...
```

### Data Access

**Kotlin**

Allows [instantiation](https://kotlinlang.org/docs/classes.html#creating-instances-of-classes) of classes without `new`, [accessing properties](https://kotlinlang.org/docs/properties.html#declaring-properties) without `get/set`, and [comparing](https://kotlinlang.org/docs/equality.html) objects
semantically with `==`:

```kotlin
val isItDept = Dept("IT").name == "IT"
```

**Java**

Java is more verbose:

```java
val isItDept = new Dept("IT").name().equals("IT");
```

### Exceptions

**Kotlin**

Kotlin does not have checked exceptions since they have fallen [out of favor](https://kotlinlang.org/docs/exceptions.html#checked-exceptions) with many developers as they increase code clutter:

```kotlin
fun foo() {
    methodWhichThrowsCheckedFooException()
}
```

**Java**

Java is more verbose, requiring workarounds that introduce code clutter:

```java
void foo() {
    try {
        methodWhichThrowsCheckedFooException();
    } catch (FooException e) {
        throw new RuntimeException(e);
    }
}
```

or

```java

@SneakyThrows
void foo() {
    methodWhichThrowsCheckedFooException();
}
```

### Immutability

**Kotlin**

Variables can be defined as `val` (immutable) and `var` (mutable). Parameters are always immutable.

```kotlin
val a = "hello"
var b = "world"

fun log(s: String) {
    println(s)
}
```

**Java**

Lombok has `val` and Java provides `var`, but neither is supported for method parameters and instance/class fields, resulting in more verbose code.

```java
val a = "hello";
var b = "world";

void log(final String s) {
    System.out.println(s);
}
```

### Null-safe Declarations

**Kotlin**

Explicit type declaration as non-nullable (default) or nullable, using the `?` null-safety operator, allowing for compile-time null safety:

```kotlin
val foo: String
val bar: String?
```

**Java**

Java has no compile-time null-safety and is more verbose, regardless of which of the following alternatives is used:

```java
// Lombok-based, generates code that throws NPE if setter for this field passes in null
@NonNull
final String foo;

// Spring-based, informs IDE to show warning and will also be interpreted by Lombok
@Nullable
final String bar;

// Denotes s as maybe absent
final Optional<String> maybeBar;
```

### Null-safe Calls

**Kotlin**

Access nullable fields via the [?.](https://kotlinlang.org/docs/null-safety.html#safe-calls) operator, defining fallbacks via the [?:](https://kotlinlang.org/docs/null-safety.html#elvis-operator) "Elvis" operator:

```kotlin
val bar = a?.bar ?: null
```

**Java**

Java is more verbose, either using

```java
val bar = a == null ? null : a.getBar();
```

or

```java
val bar = Optional.ofNullable(a)
    .map(a2 -> a2.getBar())
    .orElse(null);
```

### String templates

**Kotlin**

Reference variables directly from templated strings:

```kotlin
val s = "foo=$foo, bar=$bar"
```

**Java**

Java is harder to read, especially when using many variables, since string placeholder and referenced variable are often far apart:

```java
val s = "foo=%s, bar=%s".formatted(foo, bar);
```

### Spaces in Test Methods

**Kotlin**

Long test method names can contain spaces. They are thus more readable.

In particular, if using [Cucumber](https://cucumber.io/docs/cucumber/step-definitions/?lang=java) BDD (Behavior Driven Development) step annotations, the annotation can exactly match the method name and can be kept in sync more easily:

```kotlin
// JUnit 5
@Test
fun `Given foo When bar Then baz`() {
}

// Cucumber BDD Step
@When("user {word} logs in")
fun `user {word} logs in`(userName: String) {
}
```

**Java**

Java is harder to read and maintain due to using underscores instead of spaces. In addition, some BDD step declarations contain characters which are unsupported by Java methods, requiring further divergence:

```java
// JUnit 5
@Test
void Given_foo_When_bar_Then_baz() {
}

// Cucumber BDD Step
@When("user {word} logs in")
void user_word_logs_in(final String userName) {
}
```

### One-line Functions

**Kotlin**

One-line function without result type, `return` statement, and braces to reduce ceremony and preserve screen real estate:

```kotlin
fun add(a: Int, b: Int) = a + b
```

**Java**

Java consumes more vertical screen real estate, leading to additional scrolling and context switching:

```java
int add(int a, int b) {
    return a + b;
}
```

### Nested Functions

**Kotlin**

Nested private helper functions to improve encapsulation:

```kotlin
fun foo(): Int {
    fun helperOnlyUsedByFoo(): Int {
        //...
    }
    //...
}
```

**Java**

Splitting methods quickly results in pollution of the class namespace, making it harder to see which method a dedicated helper method is used by:

```java
int foo() {
    //...
}

private int helperOnlyUsedByFoo() {
    //...
}
```

### Extension Functions

**Kotlin**

Extend an existing Kotlin or Java API with new features, for example to provide a fluent API for improved readability:

```kotlin
// Extension setup to enrich classes with new methods; only needed once
fun Foo.toDto(): Dto = dto(this)
fun Dto.toJson(): String = json(this)

val json = foo.toDto().toJson()
```

**Java**

Java is harder to read since multiple transformations result in increasing nesting levels. Creating a fluent API is not easy for third-party libraries beyond the developer's control:

```java
val json = json(dto(entity));
```

### Scope Functions

**Kotlin**

Allows for fluent code via method chaining and `it/this` references, e.g., to log the result of a `map(a)` invocation as a side effect of returning it:

```kotlin
return doSomething(a)
    .also { log { "result=$it" } }
```

**Java**

Java is more verbose and adds increased cognitive load due to "single use" variables which pollute a method's namespace instead of leveraging a fluent API:

```java
val result = doSomething(a);

log("result={}",result);
return result;
```

## Code Samples

This chapter compares equivalent code in Java and Kotlin.

### [DtoSample](./src/main/java/uk/gleissner/javakotlin/dto/DtoSample.kt)

Purpose: JSON to DTO conversions, mapping, and logging.

- [Java](./src/main/java/uk/gleissner/javakotlin/dto/JavaDtoSample.java) Implementation
- [Kotlin](./src/main/java/uk/gleissner/javakotlin/dto/KotlinDtoSample.kt) Implementation
- [Java Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleJavaTest.java) of both Java and Kotlin implementations
- [Kotlin Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleKotlinTest.kt), ditto