package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ImplAlphaVantageAPI implements AlphaVantageAPI {
    private static volatile AlphaVantageAPI instance;
    private static final String ALPHA_VANTAGE_URL = "https://alpha-vantage.p.rapidapi.com";
    private static final String apiKey;
    private final OkHttpClient client;

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

    public static AlphaVantageAPI getInstance() {
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
    public StockData getMonthlyTimeSeries(String symbol) throws IOException {
        String url = ALPHA_VANTAGE_URL + "/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&apikey=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(StockData.class, new StockDataDeserializer())
                    .registerTypeAdapter(StockData.MonthlyTimeSeries.class, new MonthlyTimeSeriesDeserializer())
                    .create();

            return gson.fromJson(response.body().charStream(), StockData.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }
}