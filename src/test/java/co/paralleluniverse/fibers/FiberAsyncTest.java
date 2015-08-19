/*
 * Quasar: lightweight threads and actors for the JVM.
 * Copyright (c) 2013-2014, Parallel Universe Software Co. All rights reserved.
 * 
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *  
 *   or (per the licensee's choosing)
 *  
 * under the terms of the GNU Lesser General Public License version 3.0
 * as published by the Free Software Foundation.
 */
package co.paralleluniverse.fibers;

import co.paralleluniverse.common.test.TestUtil;
import co.paralleluniverse.common.util.CheckedCallable;
import co.paralleluniverse.strands.SuspendableRunnable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 *
 * @author pron
 */
public class FiberAsyncTest {
    @Rule
    public TestName name = new TestName();
    @Rule
    public TestRule watchman = TestUtil.WATCHMAN;
    
    private FiberScheduler scheduler;

    public FiberAsyncTest() {
        scheduler = new FiberForkJoinScheduler("test", 4, null, false);
    }

    interface MyCallback {
        void call(String str);

        void fail(RuntimeException e);
    }

    interface Service {
        void registerCallback(MyCallback callback);
    }
    final Service syncService = new Service() {
        @Override
        public void registerCallback(MyCallback callback) {
            callback.call("sync result!");
        }
    };
    final Service badSyncService = new Service() {
        @Override
        public void registerCallback(MyCallback callback) {
            callback.fail(new RuntimeException("sync exception!"));
        }
    };
    final ExecutorService executor = Executors.newFixedThreadPool(1);
    final Service asyncService = new Service() {
        @Override
        public void registerCallback(final MyCallback callback) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                        callback.call("async result!");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        }
    };
    final Service longAsyncService = new Service() {
        @Override
        public void registerCallback(final MyCallback callback) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        callback.call("async result!");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        }
    };
    final Service badAsyncService = new Service() {
        @Override
        public void registerCallback(final MyCallback callback) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                        callback.fail(new RuntimeException("async exception!"));
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        }
    };

    static String callService(final Service service) throws SuspendExecution, InterruptedException {
        return new MyFiberAsync() {
            @Override
            protected void requestAsync() {
                service.registerCallback(this);
            }
        }.run();
    }

    static String callService(final Service service, long timeout, TimeUnit unit) throws SuspendExecution, InterruptedException, TimeoutException {
        return new MyFiberAsync() {
            @Override
            protected void requestAsync() {
                service.registerCallback(this);
            }
        }.run(timeout, unit);
    }

    static abstract class MyFiberAsync extends FiberAsync<String, RuntimeException> implements MyCallback {
        private final Fiber fiber;

        public MyFiberAsync() {
            this.fiber = Fiber.currentFiber();
        }

        @Override
        public void call(String str) {
            super.asyncCompleted(str);
        }

        @Override
        public void fail(RuntimeException e) {
            super.asyncFailed(e);
        }
    }

    @Test
    public void testSyncCallback() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                String res = callService(syncService);
                assertThat(res, equalTo("sync result!"));
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testSyncCallbackException() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution {
                try {
                    String res = callService(badSyncService);
                    fail();
                } catch (Exception e) {
                    assertThat(e.getMessage(), equalTo("sync exception!"));
                }
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testAsyncCallback() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                String res = callService(asyncService);
                assertThat(res, equalTo("async result!"));
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testAsyncCallbackException() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution {
                try {
                    String res = callService(badAsyncService);
                    fail();
                } catch (Exception e) {
                    assertThat(e.getMessage(), equalTo("async exception!"));
                }
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testAsyncCallbackExceptionInRequestAsync() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution {
                try {
                    new FiberAsync<String, RuntimeException>() {

                        @Override
                        protected void requestAsync() {
                            throw new RuntimeException("requestAsync exception!");
                        }

                    }.run();
                    fail();
                } catch (Exception e) {
                    assertThat(e.getMessage(), equalTo("requestAsync exception!"));
                }
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testTimedAsyncCallbackNoTimeout() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                try {
                    String res = callService(asyncService, 50, TimeUnit.MILLISECONDS);
                    assertThat(res, equalTo("async result!"));
                } catch (TimeoutException e) {
                    throw new RuntimeException();
                }
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testTimedAsyncCallbackWithTimeout() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                try {
                    String res = callService(asyncService, 10, TimeUnit.MILLISECONDS);
                    fail();
                } catch (TimeoutException e) {
                }
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testInterrupt1() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution {
                try {
                    callService(longAsyncService);
                    fail();
                } catch (InterruptedException e) {
                }
            }
        }).start();

        fiber.interrupt();
        fiber.join();
    }

    @Test
    public void testInterrupt2() throws Exception {
        final Fiber fiber = new Fiber(scheduler, new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution {
                try {
                    callService(longAsyncService);
                    fail();
                } catch (InterruptedException e) {
                }
            }
        }).start();

        Thread.sleep(100);
        fiber.interrupt();
        fiber.join();
    }

    @Test
    public void testRunBlocking() throws Exception {
        final Fiber fiber = new Fiber(new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                String res = FiberAsync.runBlocking(Executors.newCachedThreadPool(), new CheckedCallable<String, InterruptedException>() {
                    public String call() throws InterruptedException {
                        Thread.sleep(300);
                        return "ok";
                    }
                });
                assertThat(res, equalTo("ok"));
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testRunBlockingWithTimeout1() throws Exception {
        final Fiber fiber = new Fiber(new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                try {
                    String res = FiberAsync.runBlocking(Executors.newCachedThreadPool(), 400, TimeUnit.MILLISECONDS, new CheckedCallable<String, InterruptedException>() {
                        public String call() throws InterruptedException {
                            Thread.sleep(300);
                            return "ok";
                        }
                    });
                    assertThat(res, equalTo("ok"));
                } catch (TimeoutException e) {
                    fail();
                }
            }
        }).start();

        fiber.join();
    }

    @Test
    public void testRunBlockingWithTimeout2() throws Exception {
        final Fiber fiber = new Fiber(new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                try {
                    String res = FiberAsync.runBlocking(Executors.newCachedThreadPool(), 100, TimeUnit.MILLISECONDS, new CheckedCallable<String, InterruptedException>() {
                        public String call() throws InterruptedException {
                            Thread.sleep(300);
                            return "ok";
                        }
                    });
                    fail();
                } catch (TimeoutException e) {
                }
            }
        }).start();

        fiber.join();
    }
}
