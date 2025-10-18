package core.basesyntax.service.impl;

import core.basesyntax.exceptions.WorkWithFileException;
import core.basesyntax.service.ReportExtractorDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReportExtractorDaoImpl implements ReportExtractorDao {

    @Override
    public List<String> getReport(InputStream inputStream) {
        List<String> linesOfReport = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8))) {
            String lineOfReport;
            while ((lineOfReport = br.readLine()) != null) {
                linesOfReport.add(lineOfReport);
            }
        } catch (IOException e) {
            throw new WorkWithFileException("Error reading report from stream", e);
        }
        return linesOfReport;
    }
}
