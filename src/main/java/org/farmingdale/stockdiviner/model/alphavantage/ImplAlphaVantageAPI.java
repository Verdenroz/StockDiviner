package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.farmingdale.stockdiviner.model.Api;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ImplAlphaVantageAPI extends Api implements AlphaVantageAPI {
    private static volatile ImplAlphaVantageAPI instance;
    private static final String ALPHA_VANTAGE_URL = "https://alpha-vantage.p.rapidapi.com";
    private static final String apiKey;

    // Load the API key from the properties file
    static {
        Properties properties = new Properties();
        try (InputStream input = ImplAlphaVantageAPI.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
            apiKey = properties.getProperty("alphaVantage.apiKey");
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("Failed to load API key from properties file");
        }
    }

    private ImplAlphaVantageAPI() {
        super(ALPHA_VANTAGE_URL);
        this.client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("x-rapidapi-key", apiKey)
                            .addHeader("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                            .build();

                    System.out.println(request.url());
                    return chain.proceed(request);
                })
                .build();
    }

    public static ImplAlphaVantageAPI getInstance() {
        if (instance == null) {
            synchronized (ImplAlphaVantageAPI.class) {
                if (instance == null) {
                    instance = new ImplAlphaVantageAPI();
                }
            }
        }
        return instance;
    }
    @Override
    public MonthlyStockData getMonthlyTimeSeries(String symbol) throws IOException {
        String url = ALPHA_VANTAGE_URL + "/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&apikey=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(MonthlyStockData.class, new MonthlyStockDataDeserializer())
                    .registerTypeAdapter(MonthlyStockData.MonthlyTimeSeries.class, new MonthlyTimeSeriesDeserializer())
                    .create();

            MonthlyStockData monthlyStockData = gson.fromJson(response.body().charStream(), MonthlyStockData.class);

            // Sort the timeSeries map by keys (dates) in descending order
            Map<LocalDate, MonthlyStockData.MonthlyTimeSeries> sortedTimeSeries = monthlyStockData.getMonthlyTimeSeries().entrySet().stream()
                    .sorted(Map.Entry.<LocalDate, MonthlyStockData.MonthlyTimeSeries>comparingByKey().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            // Set the sorted map back to the stockData object
            monthlyStockData.setMonthlyTimeSeries(sortedTimeSeries);

            return monthlyStockData;
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }

    @Override
    public WeeklyStockData getWeeklyTimeSeries(String symbol) throws IOException {
        String url = ALPHA_VANTAGE_URL + "/query?function=TIME_SERIES_WEEKLY&symbol=" + symbol + "&apikey=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(WeeklyStockData.class, new WeeklyStockDataDeserializer())
                    .registerTypeAdapter(WeeklyStockData.WeeklyTimeSeries.class, new WeeklyTimeSeriesDeserializer())
                    .create();

            WeeklyStockData weeklyStockData = gson.fromJson(response.body().charStream(), WeeklyStockData.class);

            // Sort the timeSeries map by keys (dates) in descending order
            Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> sortedTimeSeries = weeklyStockData.getWeeklyTimeSeries().entrySet().stream()
                    .sorted(Map.Entry.<LocalDate, WeeklyStockData.WeeklyTimeSeries>comparingByKey().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            // Set the sorted map back to the stockData object
            weeklyStockData.setWeeklyTimeSeries(sortedTimeSeries);

            return weeklyStockData;
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }
}