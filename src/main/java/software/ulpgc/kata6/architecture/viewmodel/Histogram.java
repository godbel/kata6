package software.ulpgc.kata6.architecture.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer> {

    private final Map<Integer, Integer> frequencies;

    private final Map<String, String> labels;

    public Histogram(Map<String, String> labels) {
        this.labels = labels;
        this.frequencies = new HashMap<>();
    }

    public void add(int value) {
        frequencies.put(value, count(value) + 1);
    }

    public int count(int value) {
        return frequencies.getOrDefault(value, 0);
    }

    public int size() {
        return frequencies.size();
    }

    public boolean isEmpty() {
        return frequencies.isEmpty();
    }

    public String title() {
        return labels.getOrDefault("Title", "");
    }

    public String xAxis() {
        return labels.getOrDefault("X", "");
    }

    public String yAxis() {
        return labels.getOrDefault("Y", "");
    }

    public String legend() {
        return labels.getOrDefault("Legend", "");
    }

    @Override
    public Iterator<Integer> iterator() {
        return frequencies.keySet().iterator();
    }
}
