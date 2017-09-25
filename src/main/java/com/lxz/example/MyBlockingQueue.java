package com.lxz.example;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaolezheng on 17/9/25.
 */
public class MyBlockingQueue {

    public static void main(String[] args) throws Exception {
        BlockingQueue queue = new BlockingQueue(10);

        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    queue.enqueue(i + "");
                    System.out.println("in->" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                while (true) {
                    Object v = queue.dequeue();
                    System.out.println("out->" + v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }


    static class BlockingQueue {
        private List queue = new LinkedList();
        private int limit = 10;

        public BlockingQueue(int limit) {
            this.limit = limit;
        }

        public synchronized void enqueue(Object item)
                throws InterruptedException {
            while (this.queue.size() == this.limit) {
                wait();
            }
            if (this.queue.size() == 0) {
                notifyAll();
            }
            this.queue.add(item);
        }

        public synchronized Object dequeue()
                throws InterruptedException {
            while (this.queue.size() == 0) {
                wait();
            }
            if (this.queue.size() == this.limit) {
                notifyAll();
            }

            return this.queue.remove(0);
        }
    }


    static class SyncBlockingQueue<E> {
        private final Object[] items;
        private int count = 0;
        private int takeIndex = 0;
        private int offerIndex = 0;
        private static final int DEFAULT_QUEUE_CAPACITY = 10;
        private Object notEmpty = new Object();
        private Object notFull = new Object();

        public SyncBlockingQueue() {
            this(DEFAULT_QUEUE_CAPACITY);
        }

        public SyncBlockingQueue(int capacity) {
            this.items = new Object[capacity];
        }

        public void offer(E item) {
            synchronized (notFull) {
                while (count == items.length) {
                    try {
                        notFull.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        notFull.notify();
                    }
                }
            }
            enqueue(item);
            synchronized (notEmpty) {
                notEmpty.notify();
            }
        }

        private void enqueue(E item) {
            synchronized (items) {
                final Object[] items = this.items;
                items[offerIndex] = item;
                if (++offerIndex == items.length) {
                    offerIndex = 0;
                }
                count++;
            }
        }

        private E take() {
            synchronized (notEmpty) {
                while (count == 0) {
                    try {
                        notEmpty.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            E object = dequeue();
            synchronized (notFull) {
                notFull.notify();
            }
            return object;
        }

        private E dequeue() {
            synchronized (items) {
                final Object[] items = this.items;
                Object object = items[takeIndex];
                items[takeIndex] = null;
                if (++takeIndex == items.length) {
                    takeIndex = 0;
                }
                count--;
                return (E) object;
            }
        }

    }

    static class LockBlockingQueue<E> {
        private Object[] items;
        private int count;
        private int takeIndex;
        private int offerIndex;
        private ReentrantLock lock = new ReentrantLock();
        private Condition notEmpty = lock.newCondition();
        private Condition notFull = lock.newCondition();
        private static final int DEFAULT_QUEUE_CAPACITY = 10;

        public LockBlockingQueue() {
            this(DEFAULT_QUEUE_CAPACITY);
        }

        public LockBlockingQueue(int capacity) {
            this.items = new Object[capacity];
        }

        public void offer(E item) throws InterruptedException {
            final ReentrantLock lock = this.lock;
            try {
                lock.lockInterruptibly();
                while (count == items.length) {
                    notFull.await();
                }
                enqueue(item);
            } finally {
                lock.unlock();
            }
        }

        private void enqueue(E item) {
            final Object[] items = this.items;
            items[offerIndex] = item;
            if (++offerIndex == count) {
                offerIndex = 0;
            }
            count++;
            notEmpty.signal();
        }

        public E take() throws InterruptedException {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();
                }
                return unqueue();
            } finally {
                lock.unlock();
            }
        }

        private E unqueue() {
            final Object[] items = this.items;
            E item = (E) items[takeIndex];
            items[takeIndex] = null;
            if (++takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            notFull.signal();
            return item;
        }
    }

}
