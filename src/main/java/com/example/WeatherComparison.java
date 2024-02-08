package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherComparison {

    public static void main(String[] args) {
        String urlTomorrow = "https://meteofor.com.ua/weather-odesa-4982/tomorrow/";
        String urlToday = "https://meteofor.com.ua/weather-odesa-4982/";

        List<Integer> temperaturesTomorrow = getTemperatures(urlTomorrow);
        List<Integer> temperaturesToday = getTemperatures(urlToday);

        System.out.println("Температуры для завтрашнего дня: " + temperaturesTomorrow);
        System.out.println("Температуры для сегодняшнего дня: " + temperaturesToday);

        List<Integer> sameTemperatures = new ArrayList<>();
        for (int tempTomorrow : temperaturesTomorrow) {
            for (int tempToday : temperaturesToday) {
                if (tempTomorrow == tempToday) {
                    sameTemperatures.add(tempTomorrow);
                    break;
                }
            }
        }

        if (!sameTemperatures.isEmpty()) {
            System.out.println("Совпадающие температуры: " + sameTemperatures);
        } else {
            System.out.println("Нет совпадающих температур.");
        }
    }

    private static List<Integer> getTemperatures(String url) {
        List<Integer> temperatures = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements temperatureElements = doc.select("span.unit.unit_temperature_c");
            for (Element element : temperatureElements) {
                String text = element.text().replaceAll("[^\\d-]", "");
                if (!text.isEmpty()) {
                    temperatures.add(Integer.parseInt(text));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return temperatures;
    }
}
