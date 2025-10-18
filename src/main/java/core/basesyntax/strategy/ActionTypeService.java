package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public interface ActionTypeService {
    public void applyTheQuantity(Storage storage, String product, int quantity);
}
