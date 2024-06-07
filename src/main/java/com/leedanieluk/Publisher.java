package com.leedanieluk;

import java.util.function.Consumer;

public interface Publisher<T> {
    void start();
    void stop();
    void subscribe(Consumer<T> consumer);
}
