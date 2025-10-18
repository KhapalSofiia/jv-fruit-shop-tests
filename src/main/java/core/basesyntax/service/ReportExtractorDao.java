package core.basesyntax.service;

import java.io.InputStream;
import java.util.List;

public interface ReportExtractorDao {
    List<String> getReport(InputStream inputStream);
}
