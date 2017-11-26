package main.threadpool;

import org.apache.commons.threadpool.DefaultThreadPool;
import org.apache.commons.threadpool.ThreadPool;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by alankrit on 26/11/17.
 */
public class ThreadPoolForConnectionRequests {
    public static ExecutorService executorService;
    static {
        executorService = Executors.newFixedThreadPool(200);
    }
}
