package com.leedanieluk.stock.model;

public class Stock {
    private final String identifier;
    private final Type type;
    private final double lastDividend;
    private final Double fixedDividend; // using boxed primitive because it can be null
    private final double parValue;

    public Stock(String identifier, Type type, double lastDividend, Double fixedDividend, double parValue) {
        this.identifier = identifier;
        this.type = type;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public double getParValue() {
        return parValue;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "identifier='" + identifier + '\'' +
                ", type=" + type +
                ", lastDividend=" + lastDividend +
                ", fixedDividend=" + fixedDividend +
                ", parValue=" + parValue +
                '}';
    }

    public enum Type {
        ORDINARY_SHARE, PREFERRED
    }
}
