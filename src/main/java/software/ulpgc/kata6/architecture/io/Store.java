package software.ulpgc.kata5.architecture.io;

import software.ulpgc.kata5.architecture.model.Movie;

import java.io.IOException;
import java.util.stream.Stream;

public interface Store {
    Stream<Movie> movies() throws IOException;
}
