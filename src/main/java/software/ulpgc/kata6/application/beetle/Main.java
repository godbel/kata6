package software.ulpgc.kata6.application.beetle;

import software.ulpgc.kata6.application.*;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    static void main() throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
        connection.setAutoCommit(false);
        importIfNeededInto(connection);
        Desktop.creat(new DatabaseStore(connection)).display().setVisible(true);
    }

    private static void importIfNeededInto(Connection connection) throws IOException, SQLException {
        if(new File("movies.db").length() > 0) return;

        Stream<Movie> movies = new RemoteStore(TsvMovieParser::from).movies().filter(movie
                -> movie.years() > 1990).filter(movie -> movie.years() < 2025);
        new DatabaseRecorder(connection).record(movies);
    }
}
