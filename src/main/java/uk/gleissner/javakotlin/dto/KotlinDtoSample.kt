package uk.gleissner.javakotlin.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class KotlinDtoSample(private val objectMapper: ObjectMapper) : DtoSample {

    private val log = KotlinLogging.logger {}

    data class Department(val name: String = "IT", val head: Employee?)

    data class Employee(val name: String?)

    override fun departmentJson(departmentName: String, departmentHeadName: String?): String {
        val department = Department(
            name = departmentName,
            head = departmentHeadName?.let { Employee(name = it) })
        return objectMapper.writeValueAsString(department).also { json ->
            log.info { "Created department JSON for (departmentName=${department.name}, employeeName=$departmentHeadName): $json" }
        }
    }

    override fun deserializedDepartmentJsonMatches(json: String, json2: String): Boolean {
        fun departmentFor(json: String) = objectMapper.readValue<Department>(json)
        return departmentFor(json) == departmentFor(json2)
    }
}
