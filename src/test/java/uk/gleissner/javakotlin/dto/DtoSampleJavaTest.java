package uk.gleissner.javakotlin.dto;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gleissner.javakotlin.AbstractSampleTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RequiredArgsConstructor
class DtoSampleJavaTest extends AbstractSampleTest {

    @Autowired
    private final JavaDtoSample javaDtoSample;

    @Autowired
    private final KotlinDtoSample kotlinDtoSample;

    @ParameterizedTest
    @MethodSource("samples")
    void Given_a_department_name_and_department_head_name_When_the_department_json_is_built_Then_it_contains_both_names(DtoSample sut) {
        assertSuccessfulBuild(sut, "IT", "Miller", """
            {"name":"IT","head":{"name":"Miller"}}""");
    }

    @ParameterizedTest
    @MethodSource("samples")
    void Given_a_department_name_and_a_null_department_head_name_When_the_Department_JSON_is_built_Then_its_head_field_is_null(DtoSample sut) {
        assertSuccessfulBuild(sut, "IT", null, """
            {"name":"IT","head":null}""");
    }

    @ParameterizedTest
    @MethodSource("samples")
    void Given_a_null_department_name_and_a_null_department_head_name_When_the_Department_JSON_is_built_Then_an_NPE_is_thrown(DtoSample sut) {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> sut.departmentJson(null, "Miller"));
    }

    private void assertSuccessfulBuild(DtoSample sut, String departmentName, String departmentHeadName, String expectedJson) {
        try (val logCaptor = LogCaptor.forRoot()) {
            val json = sut.departmentJson(departmentName, departmentHeadName);
            assertThat(json).isEqualTo(expectedJson);
            assertThat(logCaptor.getInfoLogs()).contains("Created department JSON for (departmentName=%s, employeeName=%s): %s"
                .formatted(departmentName, departmentHeadName, expectedJson));
        }
    }

    private List<DtoSample> samples() {
        return List.of(javaDtoSample, kotlinDtoSample);
    }
}
