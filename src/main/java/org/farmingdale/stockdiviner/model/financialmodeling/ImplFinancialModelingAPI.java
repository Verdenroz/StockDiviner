package org.farmingdale.stockdiviner.model.financialmodeling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ImplFinancialModelingAPI implements FinancialModelingAPI {
    private static volatile FinancialModelingAPI instance;
    private static final String FINANCIAL_MODEL_API = "https://financialmodelingprep.com/api/v3";
    private static final String apiKey;
    private final OkHttpClient client;

    // Load the API key from the properties file
    static {
        Properties properties = new Properties();
        try (InputStream input = ImplFinancialModelingAPI.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
            apiKey = properties.getProperty("financialModelingPrep.apiKey");
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("Failed to load API key from properties file");
        }
    }

    private ImplFinancialModelingAPI() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();

                    System.out.println(request.url());
                    return chain.proceed(request);
                })
                .build();
    }

    public static FinancialModelingAPI getInstance() {
        if (instance == null) {
            synchronized (ImplFinancialModelingAPI.class) {
                if (instance == null) {
                    instance = new ImplFinancialModelingAPI();
                }
            }
        }
        return instance;
    }

    @Override
    public FullQuoteData getFullQuoteData(String symbol) throws IOException {
        String url = FINANCIAL_MODEL_API + "/quote/" + symbol + "?apikey=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(FullQuoteData.class, new FullQuoteDataDeserializer())
                    .create();

            List<FullQuoteData> fullQuoteDataList = gson.fromJson(response.body().charStream(), new TypeToken<List<FullQuoteData>>() {}.getType());
            return fullQuoteDataList.getFirst(); // return the first element of the list
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }

    @Override
    public List<FullQuoteData> getSymbolList(String exchange) throws IOException {
        String url = FINANCIAL_MODEL_API + "/symbol/" + exchange + "?apikey=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<List<FullQuoteData>>() {
                    }.getType(), new ListFullQuoteDataDeserializer())
                    .create();

            return gson.fromJson(response.body().charStream(), new TypeToken<List<FullQuoteData>>() {
            }.getType());

        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }
}