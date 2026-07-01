package software.ulpgc.kata6.application;

import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class RemoteStore implements Store {

    private static final String REMOTE_URL = "https://datasets.imdbws.com/title.basics.tsv.gz";

    private final Function<String, Movie> deserializar;

    public RemoteStore(Function<String, Movie> deserializar) {
        this.deserializar = deserializar;
    }

    @Override
    public Stream<Movie> movies() throws IOException {
        return loadFrom(new URL(REMOTE_URL));
    }

    private Stream<Movie> loadFrom(URL url) {
        try {
            return loadFrom(url.openConnection());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> loadFrom(URLConnection connection) throws IOException {
        return loadFrom(unzip(connection.getInputStream()));
    }

    private Stream<Movie> loadFrom(InputStream is) throws IOException {
        return loadFrom(toReader(is));
    }

    private Stream<Movie> loadFrom(BufferedReader reader) {
        return reader.lines().skip(1).map(deserializar);
    }

    private BufferedReader toReader(InputStream is) throws IOException {
        return new BufferedReader((new InputStreamReader(is)));
    }

    private InputStream unzip(InputStream is) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(is));
    }

}
