package com.leedanieluk;

import com.leedanieluk.trade.model.Trade;
import com.leedanieluk.trade.publisher.TradePublisher;
import com.leedanieluk.trade.subscriber.TradeSubscriber;

import java.util.concurrent.TimeUnit;

/**
 * Notes: I decided to go with a one-to-many messaging design assuming that there could potentially be multiple consumers of stock prices.
 * Otherwise, a point-to-point messaging design would have been sufficient.
 * The messaging channel I've used is a LinkedBlockingQueue, mainly to allow concurrent reads and writes.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Publisher<Trade> stockPricePublisher = new TradePublisher();
        stockPricePublisher.start();
        stockPricePublisher.subscribe(new TradeSubscriber());
        TimeUnit.SECONDS.sleep(60);
        stockPricePublisher.stop();
    }
}