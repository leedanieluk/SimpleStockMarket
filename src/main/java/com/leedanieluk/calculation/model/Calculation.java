package com.leedanieluk.calculation.model;

import com.leedanieluk.trade.model.Trade;

import java.util.Map;

public class Calculation {
    private final String stockId;
    private final double dividendYield; // a) i.
    private final double peRatio; // a) ii.
    private final Trade trade; // a) iii.
    private final double vwap; // a) iv.
    private final Map<String, Double> geometricMeans; // b)

    public Calculation(String stockId, double dividendYield, double peRatio, Trade trade, double vwap, Map<String, Double> geometricMeans) {
        this.stockId = stockId;
        this.dividendYield = dividendYield;
        this.peRatio = peRatio;
        this.trade = trade;
        this.vwap = vwap;
        this.geometricMeans = geometricMeans;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "stockId='" + stockId + '\'' +
                ", dividentYield=" + dividendYield +
                ", peRatio=" + peRatio +
                ", trade=" + trade +
                ", vwap=" + vwap +
                ", geometricMeans=" + geometricMeans +
                '}';
    }
}
