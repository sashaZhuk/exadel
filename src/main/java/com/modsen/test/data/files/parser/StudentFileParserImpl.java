package com.modsen.test.data.files.parser;

import com.modsen.test.data.entity.Student;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@Component
public class StudentFileParserImpl implements StudentFileParser {
    private static final String STUDENT_INFO_SEPARATOR = ",";
    private static final String STUDENT_NAME_SEPARATED_FLOAT_REGEX = "[A-Za-z0-9]+" + STUDENT_INFO_SEPARATOR
            + "[+-]?([0-9]*[.])?[0-9]+";

    @Override
    public List<Student> parse(InputStream input) {
        Scanner scanner = new Scanner(input);
        List<Student> list = new ArrayList<>();
        while (scanner.hasNext()) {
            String studentString = scanner.nextLine();
            if (!Pattern.matches(STUDENT_NAME_SEPARATED_FLOAT_REGEX, studentString)) {
                continue;
            }

            String[] studentProperties = studentString.split(STUDENT_INFO_SEPARATOR);
            String studentName = studentProperties[0];
            float mark = Float.parseFloat(studentProperties[1]);
            Student student = new Student(studentName, mark);
            list.add(student);
        }

        return list;
    }
}
