package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyActionTypeServiceImplTest {
    private ActionTypeService supplyActionTypeService;

    @BeforeEach
    void setUp() {
        supplyActionTypeService = new SupplyActionTypeServiceImpl();
    }

    @Test
    void applyTheQuantity_nullStorage_notOk() {
        assertThrows(NullPointerException.class,
                () -> supplyActionTypeService.applyTheQuantity(null, "banana", 10));
    }

    @Test
    void applyTheQuantity_nullProductName_Ok() {
        Storage current = new Storage();
        current.getStorage().put(null, 5);
        supplyActionTypeService.applyTheQuantity(current, null, 10);

        Storage expected = new Storage();
        expected.getStorage().put(null, 15);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_negativeQuantity_notOk() {
        Storage storage = new Storage();
        assertThrows(InvalidDataException.class,
                () -> supplyActionTypeService.applyTheQuantity(storage, "banana", -10));
    }

    @Test
    void applyTheQuantity_overflowQuantity_notOk() {
        Storage storage = new Storage();
        storage.getStorage().put("banana", Integer.MAX_VALUE);
        assertThrows(ArithmeticException.class,
                () -> supplyActionTypeService.applyTheQuantity(
                        storage,
                        "banana",
                        5));
    }

    @Test
    void applyTheQuantity_allCorrect_Ok() {
        Storage current = new Storage();
        supplyActionTypeService.applyTheQuantity(current, "banana", 10);
        supplyActionTypeService.applyTheQuantity(current, "apple", 15);

        Storage expected = new Storage();
        expected.getStorage().put("banana", 10);
        expected.getStorage().put("apple", 15);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_fewSameProducts_Ok() {
        Storage current = new Storage();
        supplyActionTypeService.applyTheQuantity(current, "banana", 10);
        supplyActionTypeService.applyTheQuantity(current, "banana", 15);

        Storage expected = new Storage();
        expected.getStorage().put("banana", 25);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_emptyProductName_Ok() {
        Storage current = new Storage();
        supplyActionTypeService.applyTheQuantity(current, "", 10);

        Storage expected = new Storage();
        expected.getStorage().put("", 10);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_productNameInUkrainian_Ok() {
        Storage current = new Storage();
        current.getStorage().put("банан", 20);
        supplyActionTypeService.applyTheQuantity(current, "банан", 10);

        Storage expected = new Storage();
        expected.getStorage().put("банан", 30);
        assertEquals(expected.getStorage(), current.getStorage());
    }
}
