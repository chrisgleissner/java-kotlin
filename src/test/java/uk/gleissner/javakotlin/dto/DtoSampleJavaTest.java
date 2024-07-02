package uk.gleissner.javakotlin.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gleissner.javakotlin.AbstractSampleTest;
import uk.gleissner.javakotlin.dto.JavaDtoSample.Department;
import uk.gleissner.javakotlin.dto.JavaDtoSample.Employee;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@RequiredArgsConstructor
class DtoSampleJavaTest extends AbstractSampleTest {
    private final JavaDtoSample javaDtoSample;
    private final KotlinDtoSample kotlinDtoSample;
    private final ObjectMapper objectMapper;

    @Nested
    @TestInstance(PER_CLASS)
    class DepartmentJson {

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

    @Nested
    @TestInstance(PER_CLASS)
    class DeserializedDepartmentJsonMatches {

        private final Department department = Department.builder()
            .name("IT")
            .head(Employee.builder().name("Miller").build())
            .build();

        @ParameterizedTest
        @MethodSource("samples")
        void Given_two_matching_JSON_strings_When_they_are_compared_Then_the_result_is_true(DtoSample sut) throws JsonProcessingException {
            val departmentJson1 = objectMapper.writeValueAsString(department);
            val departmentJson2 = " " + departmentJson1 + " ";
            assertThat(sut.deserializedDepartmentJsonMatches(departmentJson1, departmentJson2)).isTrue();
        }

        @ParameterizedTest
        @MethodSource("samples")
        void Given_two_non_matching_JSON_strings_When_they_are_compared_Then_the_result_is_false(DtoSample sut) throws JsonProcessingException {
            val departmentJson1 = objectMapper.writeValueAsString(department);
            val departmentJson2 = objectMapper.writeValueAsString(department.toBuilder().name("HR").build());
            assertThat(sut.deserializedDepartmentJsonMatches(departmentJson1, departmentJson2)).isFalse();
        }


        @ParameterizedTest
        @MethodSource("samples")
        void Given_two_matching_but_syntactically_invalid_JSON_strings_When_they_are_compared_Then_an_exception_is_thrown(DtoSample sut) {
            val invalidJson = "invalidJson";
            assertThatException().isThrownBy(() -> sut.deserializedDepartmentJsonMatches(invalidJson, invalidJson));
        }

        private List<DtoSample> samples() {
            return List.of(javaDtoSample, kotlinDtoSample);
        }
    }
}
