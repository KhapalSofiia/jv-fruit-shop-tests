package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ReportDataParserService {
    List<FruitTransaction> parseReportToList(List<String> report);
}
