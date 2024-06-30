package uk.gleissner.javakotlin.dto

import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import uk.gleissner.javakotlin.AbstractSampleTest

internal class DtoSampleKotlinTest(
    @Autowired private val javaDtoSample: JavaDtoSample,
    @Autowired private val kotlinDtoSample: KotlinDtoSample
) : AbstractSampleTest() {

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