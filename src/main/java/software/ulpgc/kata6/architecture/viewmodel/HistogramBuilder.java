package software.ulpgc.kata5.architecture.viewmodel;

import software.ulpgc.kata5.architecture.model.Movie;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {

    private final Stream<Movie> movies;
    private final Map<String, String> labels;

    public static HistogramBuilder with(Stream<Movie> movies) {
        if (movies == null) throw new IllegalArgumentException("movies cannot be null");

        return new HistogramBuilder(movies);
    }

    public HistogramBuilder(Stream<Movie> movies) {
        this.movies = movies;
        this.labels = new HashMap<>();
    }

    public HistogramBuilder title(String title) {
        labels.put("Title", title);
        return this;
    }

    public HistogramBuilder xAxis(String x) {
        labels.put("X", x);
        return this;
    }

    public HistogramBuilder yAxis(String y) {
        labels.put("Y", y);
        return this;
    }

    public HistogramBuilder legend(String legend) {
        labels.put("Legend", legend);
        return this;
    }

    public Histogram use(Function<Movie, Integer> function) {
        Histogram histogram = new Histogram(labels);
        movies.map(function).forEach(histogram::add);
        return histogram;
    }
}
