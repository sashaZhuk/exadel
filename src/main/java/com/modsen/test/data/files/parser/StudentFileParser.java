package com.modsen.test.data.files.parser;

import com.modsen.test.data.entity.Student;

import java.io.InputStream;
import java.util.List;

public interface StudentFileParser {
    List<Student> parse(InputStream input);
}
