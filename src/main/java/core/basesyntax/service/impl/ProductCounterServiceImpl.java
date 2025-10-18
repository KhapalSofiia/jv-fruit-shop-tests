package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProductCounterService;
import core.basesyntax.strategy.ActionTypeService;
import core.basesyntax.strategy.Operation;
import java.util.List;
import java.util.Map;

public class ProductCounterServiceImpl implements ProductCounterService {
    private final Map<Operation, ActionTypeService> operationHandlers;

    public ProductCounterServiceImpl(Map<Operation, ActionTypeService> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public void countTheProducts(List<FruitTransaction> fruitTransactions, Storage storage) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            String productName = fruitTransaction.getProductName();
            int quantity = fruitTransaction.getQuantity();
            Operation actionCode = fruitTransaction.getActionCode();
            if (quantity < 0) {
                throw new InvalidDataException("Quantity must be "
                        + "greater than or equal to 0: " + quantity);
            }
            ActionTypeService action = operationHandlers.get(actionCode);
            action.applyTheQuantity(storage, productName, quantity);
        }
    }
}
