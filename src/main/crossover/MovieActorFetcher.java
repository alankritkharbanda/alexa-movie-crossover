package main.crossover;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alankrit on 24/11/17.
 */
public class MovieActorFetcher {
    public Set<String> getCommonActors() {
        return commonActors;
    }

    Set<String> commonActors = null;
    public MovieActorFetcher(String movie1, String movie2){
        MovieActorModel movieActorModel1 = new MovieActorModel(movie1);
        MovieActorModel movieActorModel2 = new MovieActorModel(movie2);
        movieActorModel1.getActors().retainAll(movieActorModel2.getActors());
        commonActors = new HashSet<>();
        commonActors.addAll(movieActorModel1.getActors());
    }

    public static void main(String[] args) {
        System.out.println(new MovieActorFetcher("Deathly Hallows", "half blood prince").getCommonActors());
    }
}
