package uk.gleissner.kotlinjava.dto

import io.github.oshai.kotlinlogging.KotlinLogging
import uk.gleissner.kotlinjava.CodeSample

class DtoKotlinSample : CodeSample {

    data class Department(val name: String, val head: Employee?)

    data class Employee(val name: String)

    private val log = KotlinLogging.logger {}

    override fun run(args: List<String>) {
        val department = Department(name = args[0], head = Employee(name = "John Doe"))
        val headName = department.head?.name ?: "Unknown"
        val departmentName = department.name
        log.info { "The head of $departmentName department is $headName" }
    }
}
