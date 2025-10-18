package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ProductCounterService {
    void countTheProducts(List<FruitTransaction> fruitTransactions, Storage storage);
}
