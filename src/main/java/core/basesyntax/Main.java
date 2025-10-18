package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProductCounterService;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportDataParserService;
import core.basesyntax.service.ReportExporterDao;
import core.basesyntax.service.ReportExtractorDao;
import core.basesyntax.service.impl.ProductCounterServiceImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.ReportDataParserServiceImpl;
import core.basesyntax.service.impl.ReportExporterDaoImpl;
import core.basesyntax.service.impl.ReportExtractorDaoImpl;
import core.basesyntax.strategy.ActionTypeService;
import core.basesyntax.strategy.BalanceActionTypeServiceImpl;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.PurchaseActionTypeServiceImpl;
import core.basesyntax.strategy.ReturnActionTypeServiceImpl;
import core.basesyntax.strategy.SupplyActionTypeServiceImpl;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String REPORT_FILE = "src/main/resources/finalReport.csv";

    public static void main(String[] args) {
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("report.csv");
        ReportExtractorDao fileReader = new ReportExtractorDaoImpl();
        List<String> inputReport = fileReader.getReport(inputStream);

        ReportDataParserService reportDataParserService =
                new ReportDataParserServiceImpl();

        Map<Operation, ActionTypeService> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceActionTypeServiceImpl());
        operationHandlers.put(Operation.PURCHASE,new PurchaseActionTypeServiceImpl());
        operationHandlers.put(Operation.RETURN, new ReturnActionTypeServiceImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyActionTypeServiceImpl());

        ProductCounterService productCounterService =
                new ProductCounterServiceImpl(operationHandlers);
        Storage storage = new Storage();
        List<FruitTransaction> parsedReport =
                reportDataParserService.parseReportToList(inputReport);
        productCounterService.countTheProducts(parsedReport, storage);

        ReportExporterDao reportExporterDao =
                new ReportExporterDaoImpl(REPORT_FILE);
        ReportCreator report = new ReportCreatorImpl();
        String reportWrite = report.getReport(storage);
        reportExporterDao.writeReport(reportWrite);
    }
}
