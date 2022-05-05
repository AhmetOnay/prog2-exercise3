package weather;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;

import java.util.List;

public class Weather {
    private double hightTemp;
    private double lowTemperatureAverageDaily;
    private double averageTemperature;
    private long countDailyValues;
    private List<HourlyDataPoint> windspeed;

    public Weather(double hightTemp, double lowTemperatureAverageDaily, double averageTemperature, long countDailyValues, List<HourlyDataPoint> windspeed) {
        this.hightTemp = hightTemp;
        this.lowTemperatureAverageDaily = lowTemperatureAverageDaily;
        this.averageTemperature = averageTemperature;
        this.countDailyValues = countDailyValues;
        this.windspeed = windspeed;
    }

    public double getHightTemp() {
        return hightTemp;
    }

    public double getLowTemperatureAverageDaily() {
        return lowTemperatureAverageDaily;
    }

    public long getCountDailyValues() {
        return countDailyValues;
    }

    public List<HourlyDataPoint> getWindspeed() {
        return windspeed;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }
}
