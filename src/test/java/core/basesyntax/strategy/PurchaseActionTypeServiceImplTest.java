package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseActionTypeServiceImplTest {
    private ActionTypeService purchaseActionTypeService;

    @BeforeEach
    void setUp() {
        purchaseActionTypeService = new PurchaseActionTypeServiceImpl();
    }

    @Test
    void applyTheQuantity_negativeQuantity_notOk() {
        Storage storage = new Storage();
        assertThrows(InvalidDataException.class,
                () -> purchaseActionTypeService.applyTheQuantity(storage, "banana", -10));
    }

    @Test
    void applyTheQuantity_newQuantityIsNegative_notOk() {
        Storage storage = new Storage();
        storage.getStorage().put("banana", 10);
        assertThrows(InvalidDataException.class,
                () -> purchaseActionTypeService.applyTheQuantity(
                        storage,
                        "banana",
                        15));
    }

    @Test
    void applyTheQuantity_allCorrect_Ok() {
        Storage current = new Storage();
        current.getStorage().put("banana", 15);
        current.getStorage().put("apple", 25);
        purchaseActionTypeService.applyTheQuantity(current, "banana", 10);
        purchaseActionTypeService.applyTheQuantity(current, "apple", 15);

        Storage expected = new Storage();
        expected.getStorage().put("banana", 5);
        expected.getStorage().put("apple", 10);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_emptyProductName_Ok() {
        Storage current = new Storage();
        current.getStorage().put("", 20);
        purchaseActionTypeService.applyTheQuantity(current, "", 10);

        Storage expected = new Storage();
        expected.getStorage().put("", 10);
        assertEquals(expected.getStorage(), current.getStorage());
    }

    @Test
    void applyTheQuantity_noPreviousProduct_notOk() {
        Storage storage = new Storage();
        assertThrows(NullPointerException.class,
                () -> purchaseActionTypeService.applyTheQuantity(
                        storage,
                        "banana",
                        15));
    }

    @Test
    void applyTheQuantity_productNameInUkrainian_Ok() {
        Storage current = new Storage();
        current.getStorage().put("банан", 20);
        purchaseActionTypeService.applyTheQuantity(current, "банан", 10);

        Storage expected = new Storage();
        expected.getStorage().put("банан", 10);
        assertEquals(expected.getStorage(), current.getStorage());
    }
}
