package software.ulpgc.kata5.application;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import software.ulpgc.kata5.architecture.io.Store;
import software.ulpgc.kata5.architecture.model.Movie;
import software.ulpgc.kata5.architecture.viewmodel.Histogram;
import software.ulpgc.kata5.architecture.viewmodel.HistogramBuilder;

import javax.swing.*;
import java.io.IOException;
import java.util.stream.Stream;

public class Desktop extends JFrame {

    private final Store store;

    private Desktop(Store store) {
        this.store = store;
        this.setTitle("Histogram");
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    public static Desktop creat(Store store) {
        return new Desktop(store);
    }

    public Desktop display() throws IOException{
        this.getContentPane().add(chartPanelWith(histogram()));
        return this;
    }

    private ChartPanel chartPanelWith(Histogram h) {
        return new ChartPanel(chartWith(h));
    }

    private JFreeChart chartWith(Histogram h) {
        return ChartFactory.createHistogram(h.title(), h.xAxis(), h.yAxis(), datasetWith(h));
    }

    private XYSeriesCollection datasetWith(Histogram h) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesIn(h));
        return dataset;
    }

    private XYSeries seriesIn(Histogram h) {
        XYSeries series = new XYSeries(h.legend());
        for (int bin : h) {
            series.add(bin, h.count(bin));
        }
        return series;
    }

    private Histogram histogram() throws IOException {
        return HistogramBuilder.with(movies()).title("Movies per decade").xAxis("Decade").yAxis("Count")
                .legend("Movies").use(Movie::years);
    }

    private Stream<Movie> movies() throws IOException {
        return store.movies();
    }

}