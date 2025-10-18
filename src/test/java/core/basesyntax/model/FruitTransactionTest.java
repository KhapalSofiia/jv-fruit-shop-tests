package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.strategy.Operation;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void equals_sameValues_true() {
        FruitTransaction expected = new FruitTransaction(
                Operation.BALANCE, 10, "banana");
        FruitTransaction actual = new FruitTransaction(
                Operation.BALANCE, 10, "banana");
        assertEquals(expected, actual);
    }

    @Test
    void equals_differentCode_false() {
        FruitTransaction expected = new FruitTransaction(
                Operation.BALANCE, 10, "banana");
        FruitTransaction actual = new FruitTransaction(
                Operation.PURCHASE, 10, "banana");
        assertNotEquals(expected, actual);
    }

    @Test
    void equals_differentQuantity_false() {
        FruitTransaction expected = new FruitTransaction(
                Operation.BALANCE, 10, "banana");
        FruitTransaction actual = new FruitTransaction(
                Operation.BALANCE, 20, "banana");
        assertNotEquals(expected, actual);
    }

    @Test
    void equals_differentName_false() {
        FruitTransaction expected = new FruitTransaction(
                Operation.BALANCE, 10, "banana");
        FruitTransaction actual = new FruitTransaction(
                Operation.BALANCE, 10, "apple");
        assertNotEquals(expected, actual);
    }

    @Test
    void equals_namesInUkrainian_true() {
        FruitTransaction expected = new FruitTransaction(
                Operation.BALANCE, 10, "банан");
        FruitTransaction actual = new FruitTransaction(
                Operation.BALANCE, 10, "банан");
        assertEquals(expected, actual);
    }
}
