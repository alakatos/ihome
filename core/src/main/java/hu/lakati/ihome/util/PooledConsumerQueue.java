package hu.lakati.ihome.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public abstract class PooledConsumerQueue<T> implements Consumer<T>, Runnable {

        private final Executor consumerThreadPool;
        private final ConsumerFactory<T> consumerFactory;
        private final T poisonElement;
        BlockingQueue<T> queue = new LinkedBlockingQueue<>();
        private boolean shouldStop;

        protected PooledConsumerQueue(ConsumerFactory<T> consumerFactory) {
            consumerThreadPool = Executors.newFixedThreadPool(20);
            this.consumerFactory = consumerFactory;
            this.poisonElement = createPoisonElement();
        }
    
        protected abstract T createPoisonElement();
    
        @Override
        public void accept(T element) {
            queue.offer(element);
        }
    
        public void stop() {
            queue.offer(poisonElement);
            shouldStop = true;
        }
    
        @Override
        public void run() {
            try {
                while (!shouldStop) {
                    T element = queue.take();
                    if (element == poisonElement) {
                        shouldStop = true;
                    } else {
                        consumerThreadPool.execute(() -> consumerFactory.createConsumer().accept(element));
                    }
                }
            } catch (InterruptedException e) {
                //TODO handle
            }
        }
    }
    