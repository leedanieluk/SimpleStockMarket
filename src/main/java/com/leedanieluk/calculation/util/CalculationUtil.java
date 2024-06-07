package com.leedanieluk.calculation.util;

import com.leedanieluk.stock.model.Stock;

public class CalculationUtil {
    public static double calculateDividendYield(Stock stock, double price) {
        switch (stock.getType()) {
            case ORDINARY_SHARE:
                return stock.getLastDividend() / price;
            case PREFERRED:
                return (stock.getFixedDividend() * stock.getParValue()) / price;
            default:
                throw new IllegalArgumentException("Stock does not conform to accepted type");
        }
    }

    public static double calculatePERatio(Stock stock, double price) {
        return price / stock.getLastDividend();
    }
}
