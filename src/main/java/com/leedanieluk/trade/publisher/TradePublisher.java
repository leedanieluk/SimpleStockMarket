package com.leedanieluk.trade.publisher;

import com.leedanieluk.Publisher;
import com.leedanieluk.stock.repository.StockRepository;
import com.leedanieluk.trade.model.Trade;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Potential enhancements:
 * - improve interrupt handling
 * - check with a volatile variable is publisher is running or not in case of double run to TradePublisher::start()
 * - keep track of consumers so that they can be unsubscribed independently
 * - with Java 21, we can use virtual thread pool to reduce thread pinning as consumers will be triggering blocking IO operations i.e. LinkedBlockingQueue::take()
 * - unit tests, integration tests, etc...
 */
public class TradePublisher implements Publisher<Trade> {
    private volatile boolean shouldStop = false;
    private final LinkedBlockingQueue<Trade> trades;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final StockRepository stockRepository;

    public TradePublisher() {
        this.trades = new LinkedBlockingQueue<>();
        this.stockRepository = new StockRepository();
    }

    public void start() {
        System.out.println("Starting trade publisher...");
        threadPool.submit(() -> {
            while (!shouldStop) {
                Trade trade = generateRandom();
                trades.add(trade);
                try {
                    TimeUnit.SECONDS.sleep((int) (Math.random() * 3) + 1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void stop() {
        System.out.println("Stopping trade publisher...");
        shouldStop = true;
        threadPool.shutdown();
    }

    @Override
    public void subscribe(Consumer<Trade> consumer) {
        threadPool.submit(() -> {
            while (!shouldStop) {
                try {
                    consumer.accept(trades.poll(5, TimeUnit.SECONDS)); // exit thread after 5 seconds of no trade data
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Trade generateRandom() {
        String[] identifiers = stockRepository.getUniqueIdentifiers();
        String randomIdentifier = identifiers[new Random().nextInt(identifiers.length)];
        LocalDateTime currentTime = LocalDateTime.now();
        Trade.Type randomTradeType = Trade.Type.values()[new Random().nextInt(Trade.Type.values().length)];
        long randomShares = 1L + (long) (Math.random() * 99L);
        double randomPrice = Math.random() * 100D;
        return new Trade(randomIdentifier, currentTime, randomShares, randomTradeType, randomPrice);
    }
}
