package main.crossover;

import main.httpconnection.ConnectionManager;
import main.threadpool.ThreadPoolForConnectionRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by alankrit on 24/11/17.
 */
public class MovieActorFetcher {
    private static final Logger log = LoggerFactory.getLogger(MovieActorFetcher.class);

    public Set<String> getCommonActors() {
        return commonActors;
    }

    private Set<String> commonActors = null;
    public MovieActorFetcher(final String movie1, final String movie2)  {
        long start = System.currentTimeMillis();
        final AtomicReference<MovieActorModel> movieActorModel1 = new AtomicReference<>() ;
        final AtomicReference<MovieActorModel> movieActorModel2 = new AtomicReference<>();
        Thread thread1 = new Thread() {
            public void run() {
                MovieActorModel movieActorModel = new MovieActorModel(movie1);
                movieActorModel1.set(movieActorModel);
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                MovieActorModel movieActorModel = new MovieActorModel(movie2);
                movieActorModel2.set(movieActorModel);
            }
        };

        Future future1 = ThreadPoolForConnectionRequests.executorService.submit(thread1);
        Future future2 = ThreadPoolForConnectionRequests.executorService.submit(thread2);
        try {
            future1.get();
            future2.get();
        } catch (Exception e) {
            System.out.println("MovieActorFetcher() | " + e);
        }
        movieActorModel1.get().getActors().retainAll(movieActorModel2.get().getActors());
        commonActors = new HashSet<>();
        commonActors.addAll(movieActorModel1.get().getActors());
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i ++) {
            System.out.println(new MovieActorFetcher("interstellar", "inception").getCommonActors());
        }
    }
}
