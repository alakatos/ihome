package hu.lakati.ihome.util;

import java.util.function.Consumer;

public interface ConsumerFactory<T> {
    Consumer<T> createConsumer();
}
