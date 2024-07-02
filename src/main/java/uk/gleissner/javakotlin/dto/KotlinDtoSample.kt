package uk.gleissner.javakotlin.dto

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import uk.gleissner.javakotlin.config.JacksonConfig.Companion.fromJson

@Component
class KotlinDtoSample(@Autowired private val objectMapper: ObjectMapper) : DtoSample {

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
        fun departmentFor(json: String) = objectMapper.fromJson<Department>(json)
        return departmentFor(json) == departmentFor(json2)
    }
}
