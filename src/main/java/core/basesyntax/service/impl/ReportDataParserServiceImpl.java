package core.basesyntax.service.impl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportDataParserService;
import core.basesyntax.strategy.Operation;
import java.util.ArrayList;
import java.util.List;

public class ReportDataParserServiceImpl implements ReportDataParserService {
    private static final int INDEX_OF_HEADER = 0;
    private static final String HEADER = "type,fruit,quantity";
    private static final int INDEX_OF_ACTION = 0;
    private static final int INDEX_OF_PRODUCT = 1;
    private static final int INDEX_OF_QUANTITY = 2;
    private static final int EXPECTED_LENGTH = 3;
    private static final String COLUMN_SEPARATOR = ",";

    public List<FruitTransaction> parseReportToList(List<String> report) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        if (report.isEmpty()) {
            return new ArrayList<>();
        }
        int startIndex = 0;
        if (report.get(INDEX_OF_HEADER).equalsIgnoreCase(HEADER)) {
            startIndex = 1;
        }
        for (int i = startIndex; i < report.size(); i++) {
            String line = report.get(i);
            String[] elementsOfLine = line.split(COLUMN_SEPARATOR);
            if (elementsOfLine.length != EXPECTED_LENGTH) {
                throw new InvalidDataException("Line " + (i + 1) + ": expected "
                        + EXPECTED_LENGTH + " fields but was " + elementsOfLine.length);
            }
            Operation operation = Operation.fromCode(elementsOfLine[INDEX_OF_ACTION].trim());
            int quantity = getQuantity(elementsOfLine[INDEX_OF_QUANTITY].trim(), i);
            String productName = safeOrEmpty(elementsOfLine[INDEX_OF_PRODUCT].trim());

            fruitTransactions.add(new FruitTransaction(operation, quantity, productName));
        }
        return fruitTransactions;
    }

    private static int getQuantity(String quantityString, int i) {
        int quantity = Integer.parseInt(quantityString);
        if (quantity < 0) {
            throw new InvalidDataException("Line " + (i + 1)
                    + ": negative quantity: " + quantity);
        }
        return quantity;
    }

    private String safeOrEmpty(String s) {
        return s == null ? "" : s;
    }
}
