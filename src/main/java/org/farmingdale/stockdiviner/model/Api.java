package org.farmingdale.stockdiviner.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public abstract class Api {
    protected static volatile Api instance;
    protected OkHttpClient client;
    protected final String baseUrl;

    protected Api(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();

                    System.out.println(request.url());
                    return chain.proceed(request);
                })
                .build();
    }

    public static Api getInstance() {
        return instance;
    }
}
