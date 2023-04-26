package org.common.utils.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolFactoryUtil {

    /**
     * Classify different thread pool through threadNamePrefix
     * (We can regard the thread pool of the same threadNamePrefix as the same business).
     * key: threadNamePrefix
     * value:
     */
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();

    /**
     * shutdown all thread pool
     */
    public static void shutDownAllThreadPool() {
        THREAD_POOLS.entrySet().stream().parallel().forEach(entry -> {
            ExecutorService executorService = entry.getValue();
            executorService.shutdown();
            log.info("shut down thread pool {} {}", entry.getKey(), executorService.isTerminated());
            try {
                boolean res = executorService.awaitTermination(10, TimeUnit.SECONDS);
                log.error("thread pool await termination {}", res);
            } catch (InterruptedException e) {
                log.error("thread pool never terminated");
                executorService.shutdown();
            }
        });
    }

    /**
     * create a threadFactory.
     * if threadNamePrefix is not empty, use the self built threadFactory, otherwise use defaultThreadFactory
     */
    public static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null) {
            if (daemon != null) {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();
            } else {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
            }
        }
        return Executors.defaultThreadFactory();
    }

}
