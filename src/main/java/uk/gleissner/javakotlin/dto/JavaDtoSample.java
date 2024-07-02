package uk.gleissner.javakotlin.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JavaDtoSample implements DtoSample {

    private final ObjectMapper objectMapper;

    @Builder(toBuilder = true)
    record Department(@NonNull String name, Employee head) {
    }

    @Builder(toBuilder = true)
    record Employee(@NonNull String name) {
    }

    @NotNull
    @Override
    public String departmentJson(@NonNull String departmentName, String departmentHeadName) {
        val department = Department.builder()
            .name(departmentName)
            .head(Optional.ofNullable(departmentHeadName)
                .map(employeeName -> Employee.builder().name(employeeName).build())
                .orElse(null))
            .build();
        try {
            val departmentJson = objectMapper.writeValueAsString(department);
            log.info("Created department JSON for (departmentName={}, employeeName={}): {}", departmentName, departmentHeadName, departmentJson);
            return departmentJson;
        } catch (JsonProcessingException e) { // Required since Java 22 does not support @SneakyThrows as of Lombok v1.18.34
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deserializedDepartmentJsonMatches(@NotNull String json, @NotNull String json2) {
        return departmentFor(json).equals(departmentFor(json2));
    }

    private Department departmentFor(String json) {
        try {
            return objectMapper.readValue(json, Department.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
