package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String NAME_OF_COLUMNS = "fruit,quantity";
    private static final String SYMBOL_OF_LINE_BREAK = System.lineSeparator();
    private static final String SYMBOL_OF_DATA_SEPARATION = ",";

    @Override
    public String getReport(Storage storage) {
        StringBuilder report = new StringBuilder();
        report.append(NAME_OF_COLUMNS).append(SYMBOL_OF_LINE_BREAK);
        for (Map.Entry<String, Integer> entry : storage.getStorage().entrySet()) {
            if (entry.getValue() < 0) {
                throw new InvalidDataException("The quantity can't be negative: "
                        + entry.getValue());
            }
            report.append(entry.getKey()).append(SYMBOL_OF_DATA_SEPARATION)
                    .append(entry.getValue())
                    .append(SYMBOL_OF_LINE_BREAK);
        }
        return report.toString();
    }
}
