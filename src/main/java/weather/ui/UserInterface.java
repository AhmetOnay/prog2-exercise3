package weather.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;
import weather.Weather;
import weather.ctrl.WeatherController;

public class UserInterface {

    private WeatherController ctrl = new WeatherController();

    public void getWeatherForCity1() {
        //TODO enter the coordinates - DONE Wien
        Latitude latitude = new Latitude(48.208176);
        Longitude longitude = new Longitude(16.373819
        );
        printWeather(latitude, longitude);
    }

    public void getWeatherForCity2() {
        //TODO enter the coordinates - DONE Istanbul
        Latitude latitude = new Latitude(41.008240);
        Longitude longitude = new Longitude(28.978359);
        printWeather(latitude, longitude);

    }

    public void getWeatherForCity3() {
        //TODO enter the coordinates - DONE New York
        Latitude latitude = new Latitude(40.712776);
        Longitude longitude = new Longitude(-74.005974);
        printWeather(latitude, longitude);
    }

    private void printWeather(Latitude latitude, Longitude longitude) {
        GeoCoordinates geoCoordinates = new GeoCoordinates(longitude, latitude);
        try {
            Weather weather = ctrl.process(geoCoordinates);
            System.out.println("Temperature Highest:" + weather.getHightTemp());
            System.out.println("Temperature Average Low:" + weather.getLowTemperatureAverageDaily());
            System.out.println("Average Temperature:" + weather.getAverageTemperature());
            System.out.println("Count Daily Values:" + weather.getCountDailyValues());
            System.out.print("Windspeed sorted: ") ;
            weather.getWindspeed().forEach(b ->System.out.print(b.getWindSpeed()+", "));
        } catch (ForecastException e) {
            e.printStackTrace();
        }
    }

    public void getWeatherByCoordinates() {
        //TODO read the coordinates from the cmd - DONE
        //TODO enter the coordinates - DONE
        Scanner in = new Scanner(System.in);
        double numlatitude = 0;
        double numlongitude = 0;
        System.out.println("Type Latitude:");
        while (true)
            try {
                numlatitude = Double.parseDouble(in.nextLine());
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Try again: ");
            }
        System.out.println("Type Longitude:");
        while (true)
            try {
                numlongitude = Double.parseDouble(in.nextLine());
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Try again: ");
            }


        Latitude latitude = new Latitude(numlatitude);
        Longitude longitude = new Longitude(numlongitude);
        printWeather(latitude, longitude);
    }


    public void start() {
        Menu<Runnable> menu = new Menu<>("Weather Infos");
        menu.setTitel("Wählen Sie eine Stadt aus:");
        menu.insert("a", "City 1", this::getWeatherForCity1);
        menu.insert("b", "City 2", this::getWeatherForCity2);
        menu.insert("c", "City 3", this::getWeatherForCity3);
        menu.insert("d", "City via Coordinates:", this::getWeatherByCoordinates);
        menu.insert("q", "Quit", null);
        Runnable choice;
        while ((choice = menu.exec()) != null) {
            choice.run();
        }
        System.out.println("Program finished");
    }


    protected String readLine() {
        String value = "\0";
        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = inReader.readLine();
        } catch (IOException e) {
        }
        return value.trim();
    }

    protected Double readDouble(int lowerlimit, int upperlimit) {
        Double number = null;
        while (number == null) {
            String str = this.readLine();
            try {
                number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
                System.out.println("Please enter a valid number:");
                continue;
            }
            if (number < lowerlimit) {
                System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
                System.out.println("Please enter a lower number:");
                number = null;
            }
        }
        return number;
    }
}
