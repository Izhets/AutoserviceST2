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
import ru.cs.vsu.ast2.api.account.AccountApi;
import ru.cs.vsu.ast2.api.application.ApplicationApi;
import ru.cs.vsu.ast2.api.auth.AuthApi;
import ru.cs.vsu.ast2.api.car.CarApi;
import ru.cs.vsu.ast2.api.car.brand.CarBrandApi;
import ru.cs.vsu.ast2.api.car.type.CarTypeApi;
import ru.cs.vsu.ast2.api.news.NewsApi;
import ru.cs.vsu.ast2.api.application.part.PartApi;

import java.io.IOException;

@Getter
public class AST2Service {

    private static final String API_URL = "https://autoservice-st2-app.herokuapp.com/";

    private final AuthApi authApi;
    private final AccountApi accountApi;
    private final CarTypeApi carTypeApi;
    private final CarBrandApi carBrandApi;
    private final CarApi carApi;
    private final NewsApi newsApi;
    private final PartApi partApi;
    private final ApplicationApi applicationApi;

    public AST2Service() {
        Retrofit retrofit = createRetrofit();
        authApi = retrofit.create(AuthApi.class);
        newsApi = retrofit.create(NewsApi.class);
        accountApi = retrofit.create(AccountApi.class);
        carTypeApi = retrofit.create(CarTypeApi.class);
        carBrandApi = retrofit.create(CarBrandApi.class);
        carApi = retrofit.create(CarApi.class);
        partApi = retrofit.create(PartApi.class);
        applicationApi = retrofit.create(ApplicationApi.class);
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
