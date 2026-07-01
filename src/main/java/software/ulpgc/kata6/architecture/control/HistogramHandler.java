package software.ulpgc.kata6.architecture.control;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;
import software.ulpgc.kata6.architecture.viewmodel.Histogram;
import software.ulpgc.kata6.architecture.viewmodel.HistogramBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HistogramHandler implements Handler {

    private final Store store;

    public HistogramHandler(Store store) {
        this.store = store;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String fromParam = ctx.queryParam("from");
        String toParam = ctx.queryParam("to");

        Stream<Movie> movies = store.movies();

        if(fromParam != null && toParam != null) {
            int from = Integer.parseInt(fromParam);
            int to = Integer.parseInt(toParam);
            movies = movies.filter( movie -> movie.years() >= from && movie.years() <= to);
        }

        Histogram histogram = HistogramBuilder.with(movies).use(Movie::years);

        Map<String, Integer> json = new HashMap<>();
        for(int bin : histogram){
            json.put(String.valueOf(bin), histogram.count(bin));
        }

        ctx.json(json);

    }
}
