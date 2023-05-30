package com.modsen.test.views.students;

import com.modsen.test.data.algorithm.SortingAlgorithm;
import com.modsen.test.data.entity.Student;
import com.modsen.test.data.files.Converter;
import com.modsen.test.data.files.parser.StudentFileParser;
import com.modsen.test.views.components.FileDownloadLink;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@PageTitle("Students | Test")
@Route(value = "students")
@RouteAlias(value = "")
public class StudentsView extends VerticalLayout {
    public static final String DOWNLOAD_FILE_NAME_TEMPLATE = "students-%s.txt";
    public static final String BENCHMARK_INFORMATION_TEMPLATE = "Number of records: %d. Sorting time(nanoseconds): %d";
    public static final String GRID_STUDENT_NAME_COLUMN = "name";
    public static final String GRID_STUDENT_MARK_COLUMN = "mark";
    private final StudentFileParser fileParser;
    private final Converter converter;

    private final Grid<Student> grid = new Grid<>(Student.class);
    private final ComboBox<SortingAlgorithm<Student>> algorithmsBox = new ComboBox<>("Algorithm");
    private final FileDownloadLink fileDownloadLink = new FileDownloadLink();
    private final Paragraph benchmark = new Paragraph("Benchmark");

    private List<Student> students;

    public StudentsView(StudentFileParser fileParser, Converter converter) {
        this.fileParser = fileParser;
        this.converter = converter;
        benchmark.setVisible(false);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        configureGrid();

        add(
                getFileUpload(),
                algorithmsBox,
                fileDownloadLink,
                grid,
                benchmark
        );
    }

    private Upload getFileUpload() {
        MemoryBuffer receiver = new MemoryBuffer();
        Upload fileUpload = new Upload(receiver);
        fileUpload.setAcceptedFileTypes(".txt");

        fileUpload.addSucceededListener(event -> handleFileUpload(receiver.getInputStream()));

        return fileUpload;
    }

    private void configureGrid() {
        grid.setSizeFull();

        grid.setColumns(GRID_STUDENT_NAME_COLUMN, GRID_STUDENT_MARK_COLUMN);

        grid.getColumns().forEach(col -> {
            col.setAutoWidth(true);
            col.setSortable(false);
        });
    }

    @Autowired
    private void configureAlgorithms(List<SortingAlgorithm<Student>> algorithms) {
        algorithmsBox.setItems(algorithms);
        algorithmsBox.setRequired(true);
        algorithmsBox.setValue(algorithms.iterator().next());
        algorithmsBox.addValueChangeListener(event -> onAlgorithmUpdate());
        algorithmsBox.setAllowCustomValue(false);
    }

    private void onAlgorithmUpdate() {
        if (students != null) {
            sort();
        }
    }

    private void handleFileUpload(InputStream file) {
        this.students = fileParser.parse(file);
        sort();
    }

    private void sort() {
        SortingAlgorithm<Student> algorithm = algorithmsBox.getValue();
        long start = System.nanoTime();
        algorithm.sort(students);
        long time = System.nanoTime() - start;

        updateList();
        updateLink(time);
        updateBenchmark(students.size(), time);
    }

    private void updateList() {
        grid.setItems(students);
    }

    private void updateBenchmark(int amount, long time) {
        benchmark.setText(String.format(BENCHMARK_INFORMATION_TEMPLATE.formatted(amount, time)));
        benchmark.setVisible(true);
    }

    private void updateLink(long time) {
        String filename = DOWNLOAD_FILE_NAME_TEMPLATE.formatted(LocalDateTime.now());

        InputStream stream = converter.convert(students, algorithmsBox.getValue().toString(), time);
        fileDownloadLink.setResource(new StreamResource(filename, () -> stream));
    }
}
