package com.modsen.test.data.files;

import com.modsen.test.data.entity.Student;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@Component
public class Converter {
    private static final String ALGORITHM_NAME_TEMPLATE = "Algorithm: %s";
    private static final String NUMBER_OF_RECORDS_TEMPLATE = "Number of records: %d";
    private static final String SORTING_TIME_TEMPLATE = "Sorting time(nanoseconds): %d";

    public InputStream convert(List<Student> students, String algorithmName, long time) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            for (Student student : students) {
                String studentInfo = student.getName()
                        .concat(",")
                        .concat(Float.toString(student.getMark()));

                writer.println(studentInfo);
            }

            writer.println(ALGORITHM_NAME_TEMPLATE.formatted(algorithmName));
            writer.println(NUMBER_OF_RECORDS_TEMPLATE.formatted(students.size()));
            writer.println(SORTING_TIME_TEMPLATE.formatted(time));
            writer.flush();
            writer.close();

            return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
