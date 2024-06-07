# Simple Stock Market

This is a simple realtime stock market system that performs calculations and prints the results for every new trade.
Trades are published by a `TradePublisher` and consumed by a single `TradeSubscriber` through a `LinkedBlockingQueue` channel, mainly to allow concurrent reads and writes.
I've used a one-to-many messaging design so that additional subscribers could be added and allow to scale the system horizontally, if necessary.

Running the app:
* `com.leedanieluk.Main` contains the `psvm` method

Assumptions:
* We assume that P/E ratio can take the value of `Double.POSITIVE_INFINITY` when last dividend is 0, alternatively could have set value of null, thrown a runtime exception, etc...) 

Enhancements:
* improve interrupt handling of consumers and producers
* check with a volatile variable is publisher is running or not in case of double run to TradePublisher::start()
* keep track of consumers so that they can be unsubscribed independently
* with Java 21, we can use virtual thread pool to reduce thread pinning as consumers will be triggering blocking IO operations i.e. LinkedBlockingQueue::take()
* unit tests, integration tests, etc...
* better documentation

Additional notes:
* Stock data is stored in-memory and accessible through the `StockRepository`
* `TradeSubscriber` contains most of the 'business logic'
* Trades are published randomly every 1 to 4 seconds
* The system shutdowns in around 60 seconds after starting (arbitrarily)

Sample calculation output:

`Calculation{stockId='JUI', dividentYield=0.3459666160721975, peRatio=2.890452296678581, trade=Trade{stockIdentifier='JUI', timestamp=2024-06-07T11:24:24.229, shares=50, type=SELL, price=66.48040282360736}, vwap=66.48040282360736, geometricMeans={JUI=66.48040282360736}}`
