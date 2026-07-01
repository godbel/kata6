package software.ulpgc.kata6.application.bee;

import software.ulpgc.kata6.application.Desktop;
import software.ulpgc.kata6.application.RemoteStore;
import software.ulpgc.kata6.application.TsvMovieParser;

import java.io.IOException;

public class Main {
    static void main() throws IOException {
        Desktop.creat(new RemoteStore(TsvMovieParser::from)).display().setVisible(true);
    }
}
