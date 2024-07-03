# java-kotlin

[![build](https://github.com/chrisgleissner/java-kotlin/actions/workflows/build.yaml/badge.svg)](https://github.com/chrisgleissner/java-kotlin/actions)
![Coveralls](https://img.shields.io/coverallsCoverage/github/chrisgleissner/java-kotlin)

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

# Useful Kotlin Features

Kotlin provides a number of useful features for a Java developer which are easy to learn. Properly leveraged, they result in code that's easier to read and maintain than the equivalent Java code.

## Data classes

**Kotlin**

Data holder classes with default values:

```
data class Dept(val name: String = "IT", val staffCount: Int = 0)

val itDept = Dept()
val itDept2 = itDept.copy(staffCount = 1)
val hrDept = Dept(name = "HR")
```

**Java**

Java is more verbose and requires `builder()/toBuilder()/build()` methods.

It also has no default values in `record`s, and if using Java's built-in `record`s instead of Lombok's `@Value` annotation, `@Builder.Default` (an additional Lombok annotation to mimic Kotlin defaults) cannot be used, showing
the complications that Lombok faces in trying to introduce backdoors to the Java language:

```
@Builder(toBuilder = true)
record Dept(@NonNull String name, int staffCount) {
}

val itDept = Dept.builder().name("IT").build();
val itDept2 = itDept.toBuilder().staffCount(1).build();
val hrDept = Dept.builder().name("HR");
```

## Data access

**Kotlin**

Allows to [instantiate](https://kotlinlang.org/docs/classes.html#creating-instances-of-classes) classes without `new`, [access properties](https://kotlinlang.org/docs/properties.html#declaring-properties) without `get/set`, and [compare](https://kotlinlang.org/docs/equality.html) objects
semantically with `==`:

```
val isItDept = Dept("IT").name == "IT"
```

**Java**

Java is more verbose:

```
val isItDept = new Dept("IT").getName().equals("IT");
```

## Exceptions

**Kotlin**

Kotlin does not have checked exceptions since they have fallen [out of favour](https://kotlinlang.org/docs/exceptions.html#checked-exceptions) with many developers as they increase code clutter:

```
fun foo() {
    methodWhichThrowsCheckedFooException()
}

```

**Java**

Java is more verbose, requiring workarounds that introduce code clutter:

```
void foo() {
    try {
        methodWhichThrowsCheckedFooException();
    } catch (FooException e) {
        throw new RuntimeException(e);
    }
}
```

or

```
@SneakyThrows
void foo() {
    methodWhichThrowsCheckedFooException();
}
```

## Extensions

**Kotlin**

Extend an existing Kotlin or Java API with new features, e.g. for a fluent API and improved readability:

```
// Extension setup to enrich classes with new methods; only needed once
Foo.toDto(): Dto = dto(it) 
Dto.toJson(): String = json(it) 

val json = foo.toDto().toJson()
```

**Java**

Java is harder to read since multiple transformations results in increasing nesting levels. Creating a fluent API is not easy for 3rd party libraries beyond the developer's control:

```
val json = json(dto(entity));
```

## Immutability

**Kotlin**

Variables can be defined as `val` (immutable) and `var` (mutable). Parameters are always immutable.

```
val a = "hello"
var b = "world"

fun log(s: String) {
    println(s)    
}
```

**Java**

Lombok has `val` and Java provides `var`, but neither is supported for method parameters and instance/class fields, resulting in more verbose code.

```
val a = "hello";
var b = "world";

void log(final String s) {
    System.out.println(s);    
}
```

## Null-safe declarations

**Kotlin**

Explicit type declaration as non-nullable (default) or nullable, using the `?` null-safety operator, allowing for compile-time null safety:

```
val s: String?
```

**Java**

Java has no compile-time null-safety and is more verbose, regardless which of the following alternatives is used:

```
// Lombok-based, generates code that throws NPE if setter for this field passes in null
@NonNull 
final String s;

// Spring-based, informs IDE to show warning and will also be interpreted by Lombok
@Nullable 
final String s;

// Denotes s as maybe absent
Optional<String> s; 
```

## Null-safe calls

**Kotlin**

Access nullable fields via the [?.](https://kotlinlang.org/docs/null-safety.html#safe-calls) operator, defining fallbacks via the [?:](https://kotlinlang.org/docs/null-safety.html#elvis-operator) "Elvis" operator:

```
val bar = a?.bar ?: null
```

**Java**

Java is more verbose, either using

```
val bar = a == null ? null : a.getBar();
```

or

```
val bar = Optional.ofNullable(a)
    .map(a -> a.getBar())
    .orElse(null);

```

## Scope functions

**Kotlin**

Allows for fluent code via method chaining and `it/this` references, e.g. to log the result of a `map(a)` invocation as side effect of returning it:

```
return map(a).also { log { "result=$it" } }
```

**Java**

Java is more verbose and adds increased cognitive load due to "single use" variables which pollute a methods namespace instead of a leveraging a fluent API:

```
val result = map(a); 
log("result={}", res); 
return res;
```

## Spaces in Test Methods

**Kotlin**

Long test method names can contain spaces. They are thus more readable.

In particular, if using [Cucumber](https://cucumber.io/docs/cucumber/step-definitions/?lang=java) BDD (Behavior Driven Development) step annotations, the annotation can exactly match the method name and can be kept in synch more easily:

```
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

Java is harder to read and maintain due to using underscores instead of spaces:

```
// JUnit 5
@Test
void Given_foo_When_bar_Then_baz() {
}

// Cucumber BDD Step
@When("user {word} logs in")
void user_word_logs_in(final String userName) {
}

```

## String templates

**Kotlin**

Reference variables directly from strings:

```
val s = "foo=$foo, bar=$bar"
```

**Java**

Java is harder to read, especially when using many variables:

```
val s = "foo=%s, bar=%s".formatted(foo, bar);
```

## Nested functions

**Kotlin**

Nested private helper functions to improve encapsulation:

```
fun foo(): Int { 

    fun helperOnlyUsedByFoo(): Int {
        //...
    } 
}
```

**Java**

Splitting methods quickly results in pollution of the class namespace, making it harder to see which method a dedicated helper method is used by:

```
int foo() {
    //...
}

fun helperOnlyUsedByFoo(): Int {
    //...
}
```

## One-line functions

**Kotlin**

One-line function without result type, `return` statement and braces to reduce ceremony and preserve screen real-estate:

```
fun add(a: Int, b: Int) = a + b
```

**Java Example**

Java wastes vertical screen real estate, leading to more scrolling and context switching:

```
int add(int a, int b) { 
    return a + b; 
}
```

## Kotlin Code Samples

This chapter compares equivalent code in Java and Kotlin.

### [DtoSample](./src/main/java/uk/gleissner/javakotlin/dto/DtoSample.kt)

Purpose: JSON to DTO conversions, mapping and logging.

- [Java](./src/main/java/uk/gleissner/javakotlin/dto/JavaDtoSample.java) Implementation
- [Kotlin](./src/main/java/uk/gleissner/javakotlin/dto/KotlinDtoSample.kt) Implementation
- [Java Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleJavaTest.java) of both Java and Kotlin implementation
- [Kotlin Test](./src/test/java/uk/gleissner/javakotlin/dto/DtoSampleKotlinTest.kt), ditto

