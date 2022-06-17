package ru.cs.vsu.ast2.api.news;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.cs.vsu.ast2.api.news.dto.News;

import java.util.List;

public interface NewsApi {

    @GET("/news")
    Call<List<News>> getNews();

}
