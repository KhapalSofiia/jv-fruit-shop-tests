package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProductCounterService;
import core.basesyntax.strategy.ActionTypeService;
import core.basesyntax.strategy.BalanceActionTypeServiceImpl;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.PurchaseActionTypeServiceImpl;
import core.basesyntax.strategy.ReturnActionTypeServiceImpl;
import core.basesyntax.strategy.SupplyActionTypeServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductCounterServiceImplTest {
    private Map<Operation, ActionTypeService> operationHandlers
            = new HashMap<>();
    private ProductCounterService productCounterService;

    @BeforeEach
    void setUp() {
        operationHandlers.put(Operation.BALANCE, new BalanceActionTypeServiceImpl());
        operationHandlers.put(Operation.PURCHASE,new PurchaseActionTypeServiceImpl());
        operationHandlers.put(Operation.RETURN, new ReturnActionTypeServiceImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyActionTypeServiceImpl());
        productCounterService =
                new ProductCounterServiceImpl(operationHandlers);
    }

    @AfterEach
    void cleanUp() {
        operationHandlers.clear();
    }

    @Test
    void countTheProducts_emptyTransactionList_Ok() {
        Storage testStorage = new Storage();
        Storage currectStorage = new Storage();
        List<FruitTransaction> fruitTransactions = new ArrayList<>();

        productCounterService.countTheProducts(fruitTransactions, currectStorage);

        assertEquals(currectStorage.getStorage(), testStorage.getStorage());
    }

    @Test
    void countTheProducts_allDataIsCorrect_Ok() {
        FruitTransaction t1 = new FruitTransaction(
                Operation.BALANCE, 10, "apple");
        FruitTransaction t2 = new FruitTransaction(
                Operation.BALANCE, 5, "banana");
        FruitTransaction t3 = new FruitTransaction(
                Operation.RETURN, 2, "apple");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(t1);
        fruitTransactions.add(t2);
        fruitTransactions.add(t3);

        Storage currectStorage = new Storage();
        Storage testStorage = new Storage();

        productCounterService.countTheProducts(fruitTransactions, testStorage);

        currectStorage.getStorage().put("apple", 12);
        currectStorage.getStorage().put("banana", 5);

        productCounterService.countTheProducts(fruitTransactions, currectStorage);
        assertEquals(currectStorage.getStorage(), testStorage.getStorage());
    }

    @Test
    void countTheProducts_negativeQuantity_notOk() {
        Storage testStorage = new Storage();

        FruitTransaction t1 = new FruitTransaction(
                Operation.BALANCE, -10, "apple");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();

        fruitTransactions.add(t1);

        assertThrows(InvalidDataException.class,
                () -> productCounterService.countTheProducts(fruitTransactions, testStorage));
    }

    @Test
    void countTheProducts_quantityIsNull_Ok() {
        FruitTransaction t1 = new FruitTransaction(
                Operation.BALANCE, 0, "apple");
        FruitTransaction t2 = new FruitTransaction(
                Operation.RETURN, 0, "banana");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(t1);
        fruitTransactions.add(t2);

        Storage currectStorage = new Storage();
        Storage testStorage = new Storage();

        productCounterService.countTheProducts(fruitTransactions, testStorage);

        currectStorage.getStorage().put("apple", 0);
        currectStorage.getStorage().put("banana", 0);

        assertEquals(currectStorage.getStorage(), testStorage.getStorage());
    }

    @Test
    void countTheProducts_severalDifferentOperations_Ok() {
        FruitTransaction t1 = new FruitTransaction(
                Operation.BALANCE, 10, "apple");
        FruitTransaction t2 = new FruitTransaction(
                Operation.BALANCE, 20, "banana");
        FruitTransaction t3 = new FruitTransaction(
                Operation.RETURN, 5, "apple");
        FruitTransaction t4 = new FruitTransaction(
                Operation.RETURN, 5, "banana");
        FruitTransaction t5 = new FruitTransaction(
                Operation.PURCHASE, 10, "apple");
        FruitTransaction t6 = new FruitTransaction(
                Operation.PURCHASE, 25, "banana");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(t1);
        fruitTransactions.add(t2);
        fruitTransactions.add(t3);
        fruitTransactions.add(t4);
        fruitTransactions.add(t5);
        fruitTransactions.add(t6);

        Storage currectStorage = new Storage();
        Storage testStorage = new Storage();

        productCounterService.countTheProducts(fruitTransactions, testStorage);

        currectStorage.getStorage().put("apple", 5);
        currectStorage.getStorage().put("banana", 0);

        assertEquals(currectStorage.getStorage(), testStorage.getStorage());
    }

    @Test
    void countTheProducts_operationsWithIntegerMaxValue_notOk() {
        FruitTransaction t1 = new FruitTransaction(
                Operation.BALANCE, Integer.MAX_VALUE, "apple");
        FruitTransaction t2 = new FruitTransaction(
                Operation.BALANCE, Integer.MAX_VALUE, "banana");
        FruitTransaction t3 = new FruitTransaction(
                Operation.RETURN, 5, "apple");
        FruitTransaction t4 = new FruitTransaction(
                Operation.RETURN, 10, "banana");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(t1);
        fruitTransactions.add(t2);
        fruitTransactions.add(t3);
        fruitTransactions.add(t4);

        Storage testStorage = new Storage();

        assertThrows(ArithmeticException.class,
                () -> productCounterService.countTheProducts(fruitTransactions, testStorage));
    }
}
