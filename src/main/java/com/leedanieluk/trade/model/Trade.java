package com.leedanieluk.trade.model;

import java.time.LocalDateTime;

public class Trade {
    private final String stockIdentifier;
    private final LocalDateTime timestamp;
    private final long shares;
    private final Type type;
    private final double price;

    public Trade(String stockIdentifier, LocalDateTime timestamp, long shares, Type type, double price) {
        this.stockIdentifier = stockIdentifier;
        this.timestamp = timestamp;
        this.shares = shares;
        this.type = type;
        this.price = price;
    }

    public String getStockIdentifier() {
        return stockIdentifier;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getShares() {
        return shares;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "stockIdentifier='" + stockIdentifier + '\'' +
                ", timestamp=" + timestamp +
                ", shares=" + shares +
                ", type=" + type +
                ", price=" + price +
                '}';
    }

    public enum Type {
        BUY, SELL
    }
}
