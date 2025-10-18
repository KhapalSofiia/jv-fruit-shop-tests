package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;

public class BalanceActionTypeServiceImpl implements ActionTypeService {
    @Override
    public void applyTheQuantity(Storage storage, String product, int quantity) {
        if (quantity < 0) {
            throw new InvalidDataException("Quantity can't be negative " + quantity);
        }
        if (storage.getStorage().containsKey(product)) {
            storage.getStorage().put(product, quantity);
        } else {
            storage.getStorage().put(product, quantity);
        }
    }
}
