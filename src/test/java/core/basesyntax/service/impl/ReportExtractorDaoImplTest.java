package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportExtractorDao;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportExtractorDaoImplTest {
    private ReportExtractorDao reportExtractorDao;

    @BeforeEach
    void setUp() {
        reportExtractorDao = new ReportExtractorDaoImpl();
    }

    @Test
    void getReport_inputStreamIsNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> reportExtractorDao.getReport(null));
    }

    @Test
    void getReport_validInputStream_Ok() {
        String data = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,10" + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));

        List<String> result = reportExtractorDao.getReport(inputStream);

        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,10");

        assertEquals(expected, result);
    }

    @Test
    void getReport_emptyInputStream_Ok() {
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);

        List<String> result = reportExtractorDao.getReport(inputStream);

        assertTrue(result.isEmpty());
    }
}
