package weather.ctrl;

import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {
    public int process(List<GeoCoordinates> coordinates) throws ForecastException {
        long start = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future> futureList = new ArrayList<>();
        for (GeoCoordinates coordinate : coordinates) {
            Callable<String> task = new FileTask(coordinate);
            Future<String> future = service.submit(task);
            futureList.add(future);
        }
        service.shutdown();
        long end = System.nanoTime();
        long duration = (end - start);
        System.out.println("Nanoseconds:" + duration );
        return 1;
    }

    public class FileTask implements Callable<String> {
        private final GeoCoordinates geoCoordinates;

        public FileTask(GeoCoordinates coordinates) {
            this.geoCoordinates = coordinates;
        }

        @Override
        public String call() throws Exception {

            String filename = save(geoCoordinates);

            return filename;
        }
    }
}