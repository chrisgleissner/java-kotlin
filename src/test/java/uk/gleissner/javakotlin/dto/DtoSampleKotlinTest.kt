package uk.gleissner.javakotlin.dto

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import uk.gleissner.javakotlin.AbstractSampleTest
import uk.gleissner.javakotlin.dto.KotlinDtoSample.Department
import uk.gleissner.javakotlin.dto.KotlinDtoSample.Employee

internal class DtoSampleKotlinTest(
    private val javaDtoSample: JavaDtoSample,
    private val kotlinDtoSample: KotlinDtoSample,
    private val objectMapper: ObjectMapper
) : AbstractSampleTest() {

    @Nested
    @TestInstance(PER_CLASS)
    inner class DepartmentJson {

        @ParameterizedTest
        @MethodSource("samples")
        fun `Given a department name and department head name When the Department JSON is built Then it contains both names`(sut: DtoSample) {
            assertSuccessfulBuild(sut, "IT", "Miller", """{"name":"IT","head":{"name":"Miller"}}""")
        }

        @ParameterizedTest
        @MethodSource("samples")
        fun `Given a department name and a null department head name When the Department JSON is built Then its head field is null`(sut: DtoSample) {
            assertSuccessfulBuild(sut, "IT", null, """{"name":"IT","head":null}""")
        }

        // fun `Given a null department name and a null department head name When the Department JSON is built Then an NPE is thrown`() {
        // This test is redundant in Kotlin. Defining it would result in a compile-time error since departmentName must not be null.

        private fun assertSuccessfulBuild(sut: DtoSample, departmentName: String, departmentHeadName: String?, expectedJson: String) {
            LogCaptor.forRoot().use { logCaptor ->
                val json = sut.departmentJson(departmentName, departmentHeadName)
                assertThat(json).isEqualTo(expectedJson)
                assertThat(logCaptor.infoLogs).contains(
                    "Created department JSON for (departmentName=$departmentName, employeeName=$departmentHeadName): $expectedJson"
                )
            }
        }

        private fun samples() = listOf(javaDtoSample, kotlinDtoSample)
    }

    @Nested
    @TestInstance(PER_CLASS)
    inner class DeserializedDepartmentJsonMatches {

        private val department = Department(name = "IT", head = Employee(name = "Miller"))
        private fun ObjectMapper.jsonOf(any: Any): String = writeValueAsString(any)

        @ParameterizedTest
        @MethodSource("samples")
        fun `Given two matching JSON strings When they are compared Then the result is true`(sut: DtoSample) {
            val departmentJson = objectMapper.jsonOf(department)
            val departmentJson2 = " $departmentJson "
            assertThat(sut.deserializedDepartmentJsonMatches(departmentJson, departmentJson2)).isTrue()
        }

        @ParameterizedTest
        @MethodSource("samples")
        @Throws(JsonProcessingException::class)
        fun `Given two non matching JSON strings When they are compared Then the result is false`(sut: DtoSample) {
            val departmentJson: String = objectMapper.jsonOf(department)
            val departmentJson2: String = objectMapper.jsonOf(department.copy(name = "HR"))
            assertThat(sut.deserializedDepartmentJsonMatches(departmentJson, departmentJson2)).isFalse()
        }


        @ParameterizedTest
        @MethodSource("samples")
        fun `Given two matching but syntactically invalid JSON strings When they are compared Then an exception is thrown`(sut: DtoSample) {
            val invalidJson = "invalidJson"
            assertThatException().isThrownBy { sut.deserializedDepartmentJsonMatches(invalidJson, invalidJson) }
        }

        private fun samples() = listOf(javaDtoSample, kotlinDtoSample)
    }
}