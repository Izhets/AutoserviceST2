package ru.cs.vsu.ast2.api;

import lombok.Getter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.cs.vsu.ast2.api.auth.AuthApi;
import ru.cs.vsu.ast2.api.news.NewsApi;

import java.io.IOException;

@Getter
public class AST2Service {

    private static final String API_URL = "http://192.168.0.12:8080";

    private final AuthApi authApi;
    private final NewsApi newsApi;

    public AST2Service() {
        Retrofit retrofit = createRetrofit();
        authApi = retrofit.create(AuthApi.class);
        newsApi = retrofit.create(NewsApi.class);
    }

    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .build();
                return chain.proceed(newRequest);
            }
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
