package uk.gleissner.kotlinjava.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import uk.gleissner.kotlinjava.CodeSample;

import java.util.List;

@Slf4j
public class DtoJavaSample implements CodeSample {

    @Override
    public void run(List<String> args) {
        val department = Department.builder().name(args.getFirst()).head(Employee.builder().name("John Doe").build()).build();
        val headName = department.head() != null ? department.head().name() : "Unknown";
        val departmentName = department.name();
        log.info("The head of {} department is {}", departmentName, headName);
    }

    @Builder
    record Department(@NonNull String name, Employee head) {
    }

    @Builder
    record Employee(@NonNull String name) {
    }
}
