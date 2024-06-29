package uk.gleissner.javakotlin.dto

import io.github.oshai.kotlinlogging.KotlinLogging
import uk.gleissner.javakotlin.CodeSample

class DtoKotlinSample : CodeSample {

    private val log = KotlinLogging.logger {}

    override fun run(args: List<String>) {
        val department = Department(name = args[0], head = Employee(name = "John Doe"))
        log.info { "The head of ${department.name} department is ${department.head?.name ?: "Unknown"}" }
    }

    data class Department(val name: String, val head: Employee?)

    data class Employee(val name: String)
}
