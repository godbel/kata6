package software.ulpgc.kata6.application.bumblebee;

import io.javalin.Javalin;
import software.ulpgc.kata6.application.DatabaseRecorder;
import software.ulpgc.kata6.application.DatabaseStore;
import software.ulpgc.kata6.application.RemoteStore;
import software.ulpgc.kata6.application.TsvMovieParser;
import software.ulpgc.kata6.architecture.control.HistogramHandler;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    static void main() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
        importIfNeededInto(connection);
        Store store = new DatabaseStore(connection);

        Javalin app = Javalin.create().start(9000);
        app.get("/histogram", new HistogramHandler(store));

        System.out.println("Servicio web iniciado en http://localhost:9000/histogram");
    }

    private static void importIfNeededInto(Connection connection) throws SQLException, IOException{
        if(new File("movies.db").length() > 0){
            return;

        }
        Stream<Movie> movies = new RemoteStore(TsvMovieParser::from).movies();
        new DatabaseRecorder(connection).record(movies);
    }
}
