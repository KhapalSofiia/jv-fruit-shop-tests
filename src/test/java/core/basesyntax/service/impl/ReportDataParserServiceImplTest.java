package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportDataParserService;
import core.basesyntax.strategy.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportDataParserServiceImplTest {
    private ReportDataParserService reportDataParserService;

    @BeforeEach
    void setUp() {
        reportDataParserService = new ReportDataParserServiceImpl();
    }

    @Test
    void parseReportToList_listIsEmpty_Ok() {
        List<String> report = new ArrayList<>();

        List<FruitTransaction> reportExpected = new ArrayList<>();
        List<FruitTransaction> reportCurrent = reportDataParserService.parseReportToList(report);

        assertEquals(reportExpected, reportCurrent);
    }
    
    @Test
    void parseReportToList_listWithHeader_Ok() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana,10");
        report.add("p,banana,5");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 10, "banana"));
        expected.add(new FruitTransaction(Operation.PURCHASE, 5, "banana"));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_listWithoutHeader_Ok() {
        List<String> report = new ArrayList<>();
        report.add("b,banana,10");
        report.add("p,banana,5");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 10, "banana"));
        expected.add(new FruitTransaction(Operation.PURCHASE, 5, "banana"));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_notThreeElements_notOk() {
        List<String> report = new ArrayList<>();
        report.add("banana,10");
        assertThrows(InvalidDataException.class,
                () -> reportDataParserService.parseReportToList(report));
    }

    @Test
    void parseReportToList_unknownOperationCode_notOk() {
        List<String> report = new ArrayList<>();
        report.add("f,banana,10");
        assertThrows(InvalidDataException.class,
                () -> reportDataParserService.parseReportToList(report));
    }

    @Test
    void parseReportToList_pointColumnSeparator_notOk() {
        List<String> report = new ArrayList<>();
        report.add("b.banana.10");
        assertThrows(InvalidDataException.class,
                () -> reportDataParserService.parseReportToList(report));
    }

    @Test
    void parseReportToList_negativeQuantity_notOk() {
        List<String> report = new ArrayList<>();
        report.add("b,banana,-10");
        assertThrows(InvalidDataException.class,
                () -> reportDataParserService.parseReportToList(report));
    }

    @Test
    void parseReportToList_emptyProductName_Ok() {
        List<String> report = new ArrayList<>();
        report.add("b,,10");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 10, ""));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_emptyQuantity_notOk() {
        List<String> report = new ArrayList<>();
        report.add("b,banana,");
        assertThrows(InvalidDataException.class,
                () -> reportDataParserService.parseReportToList(report));
    }

    @Test
    void parseReportToList_onlyHeader_Ok() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_withSpaces_Ok() {
        List<String> report = new ArrayList<>();
        report.add(" b , banana , 10 ");
        report.add(" p , banana , 5 ");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 10, "banana"));
        expected.add(new FruitTransaction(Operation.PURCHASE, 5, "banana"));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_nullQuantity_Ok() {
        List<String> report = new ArrayList<>();
        report.add("b,banana,0");
        report.add("p,banana ,0");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 0, "banana"));
        expected.add(new FruitTransaction(Operation.PURCHASE, 0, "banana"));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_quantityWithPlus_Ok() {
        List<String> report = new ArrayList<>();
        report.add("b,banana,+10");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 10, "banana"));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_quantityIsMax_notOk() {
        List<String> report = new ArrayList<>();
        report.add("b,banana,2147483648");
        assertThrows(NumberFormatException.class,
                () -> reportDataParserService.parseReportToList(report));
    }

    @Test
    void parseReportToList_headerDifferentCase_Ok() {
        List<String> report = new ArrayList<>();
        report.add("Type,FrUit,QuantitY");
        report.add("b,banana,3");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 3, "banana"));
        assertEquals(expected, current);
    }

    @Test
    void parseReportToList_productNameUkrainian_Ok() {
        List<String> report = new ArrayList<>();
        report.add("b,банан,3");
        List<FruitTransaction> current = reportDataParserService.parseReportToList(report);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, 3, "банан"));
        assertEquals(expected, current);
    }
}
