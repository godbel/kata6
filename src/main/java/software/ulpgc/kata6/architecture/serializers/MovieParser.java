package software.ulpgc.kata6.architecture.serializers;

import software.ulpgc.kata6.architecture.model.Movie;

public interface MovieParser {
    Movie from(String line);
}
