package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.DailyDataPoint;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;
import weather.Weather;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherController {
    
    public static String apiKey = "ab5c55091bfde0864c41b337f1c66af5";



    public Weather process(GeoCoordinates location) throws ForecastException {
            System.out.println("process "+location); //$NON-NLS-1$
            Forecast data = getData(location);

            //TODO implement Error handling -DONE

            //TODO implement methods for
            // highest temperature - DONE
            // average temperature - DONE
            // count the daily values - DONE
            // implement a Comparator for the Windspeed
            //Supplier<Stream<DailyDataPoint>> weatherDaily = () -> data.getDaily().getData().stream();
            List<DailyDataPoint> weatherDaily = data.getDaily().getData();
            List<HourlyDataPoint> weatherHourly = data.getHourly().getData();
            double highTemp = getHighestTemperatureOfTheLastDays(weatherDaily);
            double lowTemperatureAverageDaily = getLowTemperatureAverageDaily(weatherDaily);
            double lowTemp = getLowestTemperatureOfTheLastDays(weatherDaily);
            double averageTemperature = (highTemp +lowTemp)/2;
            long count = getCountDailyEntries(weatherDaily);
            List<HourlyDataPoint> sortedWindspeed = getsortedWindspeed(weatherHourly);

            return new Weather(highTemp, lowTemperatureAverageDaily, averageTemperature, count, sortedWindspeed);
	}

    public Forecast getData(GeoCoordinates location) throws ForecastException {
		ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(apiKey))
                .location(location)
                .build();

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();

        return client.forecast(request);
    }

    public double getHighestTemperatureOfTheLastDays(List<DailyDataPoint> list){
        return list.stream().mapToDouble(DailyDataPoint::getTemperatureHigh)
                .max().getAsDouble();
    }

    public double getLowestTemperatureOfTheLastDays(List<DailyDataPoint> list){
        return list.stream().mapToDouble(DailyDataPoint::getTemperatureLow)
                .max().getAsDouble();
    }

    public double getLowTemperatureAverageDaily(List<DailyDataPoint> list){
        return list.stream().mapToDouble(DailyDataPoint::getTemperatureLow)
                .average().getAsDouble();
    }

    public long getCountDailyEntries(List<DailyDataPoint> list){
        return list.stream().count();
    }

    public List<HourlyDataPoint> getsortedWindspeed(List<HourlyDataPoint> list){
        return list.stream().sorted(Comparator.comparingDouble(HourlyDataPoint::getWindSpeed))
                .collect(Collectors.toList());
    }

    public class MyForecastException extends ForecastException {
        public MyForecastException(String message) {
            super(message);
        }

        public MyForecastException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}


