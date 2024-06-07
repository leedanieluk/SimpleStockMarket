package com.leedanieluk.trade.subscriber;

import com.leedanieluk.Consumer;
import com.leedanieluk.stock.model.Stock;
import com.leedanieluk.stock.repository.StockRepository;
import com.leedanieluk.calculation.model.Calculation;
import com.leedanieluk.trade.model.Trade;
import com.leedanieluk.calculation.util.CalculationUtil;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains most of the business logic.
 */
public class TradeSubscriber implements Consumer<Trade> {
    private final Map<String, ArrayDeque<Trade>> trades;
    private final Map<String, Double> geometricMeans;
    private final StockRepository stockRepository;

    public TradeSubscriber() {
        this.trades = new HashMap<>();
        this.geometricMeans = new HashMap<>();
        this.stockRepository = new StockRepository();
    }

    @Override
    public void accept(Trade trade) {
        String stockId = trade.getStockIdentifier();
        Stock tradedStock = stockRepository.getStock(stockId);

        // a) i. Calculate dividend yield
        double dividendYield = CalculationUtil.calculateDividendYield(tradedStock, trade.getPrice());

        // a) ii. Calculate P/E ratio (Note: we assume that this can take the value of Double.POSITIVE_INFINITY, alternatively could have set value of null, thrown a runtime exception, etc...) when last dividend is 0
        double peRatio = CalculationUtil.calculatePERatio(tradedStock, trade.getPrice());

        // a) iii. Capture trade
        if (!trades.containsKey(trade.getStockIdentifier())) {
            trades.put(trade.getStockIdentifier(), new ArrayDeque<>());
        }
        trades.get(stockId).addFirst(trade);

        // a) iv. Calculate Volume Weighted Stock price (VWAP). Note: time complexity O(n)
        ArrayDeque<Trade> tradesForStock = trades.get(stockId);
        LocalDateTime currentTimestamp = LocalDateTime.now();
        double volume = 0, quantity = 0;
        for (Trade t : tradesForStock) {
            if (t.getTimestamp().isAfter(currentTimestamp.minusMinutes(15))) {
                volume += t.getPrice() * t.getShares();
                quantity += t.getShares();
            } else {
                break;
            }
        }
        double vwap = volume / quantity;

        // b) Dynamically calculate all Share Index using the geometric mean of prices for stocks in constant time complexity
        if (!geometricMeans.containsKey(stockId)) {
            geometricMeans.put(stockId, 1.0);
        }
        // first we get the number of trades made on the stock so far i.e. (n - 1) as we haven't accounted for the current trade yet
        int n = trades.containsKey(stockId) ? trades.get(stockId).size() - 1 : 0;

        // we apply power of n to geometric to extract the net product
        double netProduct = Math.pow(geometricMeans.get(stockId), n);

        // we multiply net product by trade price
        netProduct *= trade.getPrice();

        // we apply square root of n  i.e. (n + 1) but we previously subtracted 1
        double geometricMean = Math.pow(netProduct, 1.0 / (n + 1));

        // update geometric mean for stock
        geometricMeans.put(stockId, geometricMean);

        Calculation calculation = new Calculation(stockId, dividendYield, peRatio, trade, vwap, geometricMeans);

        // print calculations
        System.out.println(calculation);
    }
}
