package uk.gleissner.javakotlin.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import uk.gleissner.javakotlin.CodeSample;

import java.util.List;
import java.util.Optional;

@Slf4j
public class DtoJavaSample implements CodeSample {

    @Override
    public void run(List<String> args) {
        val department = Department.builder().name(args.getFirst()).head(Employee.builder().name("John Doe").build()).build();
        if (log.isInfoEnabled()) {
            val headName = Optional.ofNullable(department.head()).map(head -> head.name).orElse("Unknown");
            val departmentName = department.name();
            log.info("The head of {} department is {}", departmentName, headName);
        }
    }

    @Builder
    record Department(@NonNull String name, Employee head) {
    }

    @Builder
    record Employee(@NonNull String name) {
    }
}
