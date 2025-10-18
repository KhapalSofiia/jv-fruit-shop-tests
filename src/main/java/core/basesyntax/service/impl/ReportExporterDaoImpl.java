package core.basesyntax.service.impl;

import core.basesyntax.exceptions.WorkWithFileException;
import core.basesyntax.service.ReportExporterDao;
import java.io.FileWriter;
import java.io.IOException;

public class ReportExporterDaoImpl implements ReportExporterDao {
    private final String fileName;

    public ReportExporterDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void writeReport(String report) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(report);
        } catch (IOException e) {
            throw new WorkWithFileException("Error writing report to " + fileName, e);
        }
    }
}
