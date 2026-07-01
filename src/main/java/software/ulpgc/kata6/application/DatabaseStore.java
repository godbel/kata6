package software.ulpgc.kata5.application;

import software.ulpgc.kata5.architecture.io.Store;
import software.ulpgc.kata5.architecture.model.Movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Stream;

public class DatabaseStore implements Store {

    private final Connection connection;

    public DatabaseStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stream<Movie> movies() throws IOException {
        try {
            return moviesIn(query());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> moviesIn(ResultSet resultSet) throws SQLException {
        return Stream.generate(() -> {
            try {
                return movieIn(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).takeWhile(Objects::nonNull);
    }

    private ResultSet query() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM MOVIES");
    }

    private Movie movieIn(ResultSet resultSet) throws SQLException {
        return resultSet.next() ? readFrom(resultSet) : null;
    }

    private static Movie readFrom(ResultSet resultSet) throws SQLException {
        return new Movie(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3));
    }
}
