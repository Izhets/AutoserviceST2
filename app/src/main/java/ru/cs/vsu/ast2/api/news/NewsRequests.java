package ru.cs.vsu.ast2.api.news;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.news.dto.News;

import java.util.List;
import java.util.concurrent.Callable;

public class NewsRequests {

    private static final NewsApi newsApi = App.getAST2Service().getNewsApi();

    public NewsRequests() {
    }

    public void getNews(Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<News>> call = newsApi.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get news success");
                    AppSession.getInstance().setNews(response.body());
//                    AppSession.getInstance().setNewsIdMap(response.body().stream()
//                            .collect(Collectors.toMap(News::getId, Function.identity())));
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Log.e("REQUEST", "Get news failure with code = " + response.code());
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("REQUEST", "Get news id failure" );
                t.printStackTrace();
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
