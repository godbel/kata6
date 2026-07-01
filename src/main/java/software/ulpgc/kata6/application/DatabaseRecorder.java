package software.ulpgc.kata5.application;

import software.ulpgc.kata5.architecture.io.Recorder;
import software.ulpgc.kata5.architecture.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {

    private final Connection connection;
    private final PreparedStatement statement;
    private int count;

    public DatabaseRecorder(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
        this.statement = connection.prepareStatement("INSERT INTO movies (title, years, duration) VALUES (?, ?, ?)");
        this.count = 0;
    }

    private void createTableIfNotExists() throws SQLException {
        connection.createStatement()
                .execute("CREATE TABLE IF NOT EXISTS movies (title TEXT, years INTEGER, duration INTEGER)");
    }

    @Override
    public void record(Stream<Movie> movies) {
        try {
            movies.forEach(movie -> {
                try {
                    record(movie);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            flush();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void record(Movie movie) throws SQLException {
        write(movie);
        flushIfNeeded();
    }

    private void flushIfNeeded() throws SQLException {
        if (mustFlush()) flush();
    }

    private boolean mustFlush() {
        return ++count % 10_000 == 0;
    }

    private void write(Movie movie) throws SQLException {
        statement.setString(1, movie.name());
        statement.setInt(2, movie.years());
        statement.setInt(3, movie.duration());
        statement.addBatch();
    }

    private void flush() throws SQLException {
        statement.executeBatch();
    }
}