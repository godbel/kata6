package software.ulpgc.kata5.application;

import software.ulpgc.kata5.architecture.model.Movie;

public class TsvMovieParser {

    public static Movie from(String line) {
        return from(line.split("\t"));
    }

    private static Movie from(String [] fields) {
        return new Movie(fields[2], toInt(fields[5]), toInt(fields[7]));
    }

    private static int toInt(String value) {
        if(isEmpty(value)) return -1;
        return Integer.parseInt(value);
    }

    private static boolean isEmpty(String value) {
        return value.equals("\\N");
    }
}
