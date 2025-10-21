package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void countTheProducts_emptyProductName_Ok() {
        Storage current = new Storage();
        current.getStorage().put("", 15);
        String currentReport = reportCreator.getReport(current);

        String expected = "fruit,quantity" + System.lineSeparator()
                + ",15" + System.lineSeparator();
        assertEquals(currentReport, expected);
    }

    @Test
    void countTheProducts_emptyStorage_Ok() {
        Storage current = new Storage();

        String currentReport = reportCreator.getReport(current);

        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(currentReport, expected);
    }

    @Test
    void countTheProducts_productNameInUkrainian_Ok() {
        Storage current = new Storage();
        current.getStorage().put("банан", 15);
        String currentReport = reportCreator.getReport(current);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "банан,15" + System.lineSeparator();
        assertEquals(currentReport, expected);
    }

    @Test
    void countTheProducts_negativeQuantity_notOk() {
        Storage current = new Storage();
        current.getStorage().put("банан", -15);

        assertThrows(InvalidDataException.class,
                () -> reportCreator.getReport(current));
    }

    @Test
    void countTheProducts_nullProductName_notOk() {
        Storage current = new Storage();
        current.getStorage().put(null, -15);

        assertThrows(InvalidDataException.class,
                () -> reportCreator.getReport(current));
    }

    @Test
    void countTheProducts_quantityMaxValue_Ok() {
        Storage current = new Storage();
        current.getStorage().put("banana", Integer.MAX_VALUE);
        String currentReport = reportCreator.getReport(current);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,2147483647" + System.lineSeparator();
        assertEquals(currentReport, expected);
    }
}
