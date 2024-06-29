package uk.gleissner.kotlinjava.fixture

import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import uk.gleissner.kotlinjava.CodeSample

abstract class AbstractCodeSampleSimpleTest(
    private val javaCodeSample: CodeSample,
    private val kotlinCodeSample: CodeSample
) {

    @ParameterizedTest
    @MethodSource("args")
    fun `assert that logs match`(args: List<String>) {
        assertThat(javaCodeSample.resultFor(args)).isEqualTo(kotlinCodeSample.resultFor(args))
    }

    private data class CodeSampleResult(val infoLogs: List<String>, val warnLogs: List<String>, val errorLogs: List<String>, val exceptionInfo: ExceptionInfo?)

    private data class ExceptionInfo(val className: String)

    private fun CodeSample.resultFor(args: List<String>): CodeSampleResult {
        val logCaptor = LogCaptor.forRoot()
        var exceptionInfo: ExceptionInfo? = null
        try {
            run(args)
        } catch (e: Exception) {
            exceptionInfo = ExceptionInfo(e.javaClass.name)
        }
        return CodeSampleResult(logCaptor.infoLogs, logCaptor.warnLogs, logCaptor.errorLogs, exceptionInfo)
    }

    companion object {
        @JvmStatic
        fun args() = listOf(
            listOf(null),
            listOf(""),
            listOf("Name")
        )
    }
}
