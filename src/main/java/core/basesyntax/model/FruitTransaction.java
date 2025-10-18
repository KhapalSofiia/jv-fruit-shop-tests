package core.basesyntax.model;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.strategy.Operation;
import java.util.Objects;

public class FruitTransaction {
    private Operation code;
    private int quantity;
    private String productName;

    public FruitTransaction(Operation code, int quantity, String productName) {
        this.code = code;
        this.quantity = quantity;
        this.productName = productName;
    }

    public Operation getActionCode() {
        return code;
    }

    public void setCode(Operation actionCode) {
        this.code = actionCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidDataException("Quantity must not be negative: "
                    + quantity);
        }
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity
                && code == that.code
                && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, quantity, productName);
    }
}
