package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.WorkWithFileException;
import core.basesyntax.service.ReportExporterDao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReportExporterDaoImplTest {
    @Test
    void writeReport_NullFileName_notOk() {
        ReportExporterDao reportExporterDao = new ReportExporterDaoImpl(null);
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();
        assertThrows(NullPointerException.class, () -> reportExporterDao.writeReport(report));
    }

    @Test
    void writeReport_emptyReport_Ok() throws IOException {
        String fileName = "empty-report.csv";
        ReportExporterDao reportExporter = new ReportExporterDaoImpl(fileName);

        reportExporter.writeReport("");

        assertTrue(Files.exists(Path.of(fileName)));
        assertEquals("", Files.readString(Path.of(fileName)));
    }

    @Test
    void writeReport_usualReport_Ok() throws IOException {
        String fileName = "empty-report.csv";
        ReportExporterDao reportExporter = new ReportExporterDaoImpl(fileName);

        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();
        reportExporter.writeReport(report);

        assertTrue(Files.exists(Path.of(fileName)));
        assertEquals(report, Files.readString(Path.of(fileName)));
    }

    @Test
    void writeReport_invalidPath_notOk() throws IOException {
        String fileName = "src/example/empty-report.csv";
        ReportExporterDao reportExporter = new ReportExporterDaoImpl(fileName);

        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();

        assertThrows(WorkWithFileException.class,
                () -> reportExporter.writeReport(report));
    }

    @Test
    void writeReport_overwritesFile_Ok() throws IOException {
        String fileName = "existed-report.csv";
        ReportExporterDao reportExporter = new ReportExporterDaoImpl(fileName);

        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();
        reportExporter.writeReport(report);

        assertTrue(Files.exists(Path.of(fileName)));
        assertEquals(report, Files.readString(Path.of(fileName)));
    }
}
