package core.basesyntax.strategy;

import core.basesyntax.exceptions.InvalidDataException;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private final String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Operation fromCode(String code) {
        String cleanCode = trimOrEmpty(code);
        for (Operation op : Operation.values()) {
            if (op.getCode().equals(cleanCode)) {
                return op;
            }
        }
        throw new InvalidDataException("Invalid operation code: " + code);
    }

    private static String trimOrEmpty(String s) {
        return s == null ? "" : s.trim();
    }
}
