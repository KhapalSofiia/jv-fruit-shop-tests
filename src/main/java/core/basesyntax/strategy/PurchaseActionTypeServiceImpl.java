package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;

public class PurchaseActionTypeServiceImpl implements ActionTypeService {
    @Override
    public void applyTheQuantity(Storage storage, String product, int quantity) {
        if (quantity < 0) {
            throw new InvalidDataException("Quantity can't be negative " + quantity);
        }
        Integer currentValue = storage.getStorage().get(product);
        if (currentValue - quantity < 0) {
            throw new InvalidDataException("New quantity can't be negative: "
                    + (currentValue - quantity) + " for product: " + product);
        }
        storage.getStorage().put(product, currentValue - quantity);
    }
}
