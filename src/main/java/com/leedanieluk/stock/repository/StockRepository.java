package com.leedanieluk.stock.repository;

import com.leedanieluk.stock.model.Stock;

import java.util.HashMap;
import java.util.Map;

public class StockRepository {
    // load stocks data into memory
    private final static Map<String, Stock> stocks;
    static {
        stocks = new HashMap<>();
        stocks.put("TEA", new Stock("TEA", Stock.Type.ORDINARY_SHARE, 0, null, 150));
        stocks.put("COF", new Stock("COF", Stock.Type.PREFERRED, 8, 0.04, 100));
        stocks.put("MIL", new Stock("MIL", Stock.Type.ORDINARY_SHARE, 8, null, 100));
        stocks.put("JUI", new Stock("JUI", Stock.Type.ORDINARY_SHARE, 23, null, 70));
        stocks.put("WAT", new Stock("WAT", Stock.Type.ORDINARY_SHARE, 13, null, 250));
    }

    public Stock getStock(String identifier) {
        return stocks.get(identifier);
    }

    public String[] getUniqueIdentifiers() {
        return new String[] { "TEA", "COF", "MIL", "JUI", "WAT" };
    }
}
