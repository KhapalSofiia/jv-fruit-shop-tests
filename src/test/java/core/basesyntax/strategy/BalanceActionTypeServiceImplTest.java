package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceActionTypeServiceImplTest {
    private ActionTypeService balanceActionTypeService;

    @BeforeEach
    void setUp() {
        balanceActionTypeService = new BalanceActionTypeServiceImpl();
    }

    @Test
    void applyTheQuantity_negativeQuantity_notOk() {
        Storage storage = new Storage();
        assertThrows(InvalidDataException.class,
                () -> balanceActionTypeService.applyTheQuantity(storage, "banana", -10));
    }

    @Test
    void applyTheQuantity_overflowQuantity_notOk() {
        Storage storage = new Storage();
        assertThrows(InvalidDataException.class,
                () -> balanceActionTypeService.applyTheQuantity(
                        storage,
                        "banana",
                        Integer.MAX_VALUE + 1));
    }

    @Test
    void applyTheQuantity_allCorrect_Ok() {
        Storage current = new Storage();
        balanceActionTypeService.applyTheQuantity(current, "banana", 10);
        balanceActionTypeService.applyTheQuantity(current, "apple", 15);

        Storage expected = new Storage();
        expected.getStorage().put("banana", 10);
        expected.getStorage().put("apple", 15);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_fewSameProducts_Ok() {
        Storage current = new Storage();
        balanceActionTypeService.applyTheQuantity(current, "banana", 10);
        balanceActionTypeService.applyTheQuantity(current, "banana", 15);

        Storage expected = new Storage();
        expected.getStorage().put("banana", 15);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_emptyProductName_Ok() {
        Storage current = new Storage();
        balanceActionTypeService.applyTheQuantity(current, "", 10);

        Storage expected = new Storage();
        expected.getStorage().put("", 10);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_productNameInUkrainian_Ok() {
        Storage current = new Storage();
        balanceActionTypeService.applyTheQuantity(current, "банан", 10);

        Storage expected = new Storage();
        expected.getStorage().put("банан", 10);
        assertEquals(expected.getStorage(), current.getStorage());
    }
}
