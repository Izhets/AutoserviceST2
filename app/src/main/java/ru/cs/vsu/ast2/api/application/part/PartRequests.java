package ru.cs.vsu.ast2.api.application.part;

import android.util.Log;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.application.part.dto.Part;
import ru.cs.vsu.ast2.api.application.part.dto.PartCategory;
import ru.cs.vsu.ast2.util.AuthUtil;

import java.util.List;
import java.util.concurrent.Callable;

public class PartRequests {

    private static final PartApi partApi = App.getAST2Service().getPartApi();

    public PartRequests() {
    }

    public void getPartCategories(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<PartCategory>> call = partApi.getPartCategory(AuthUtil.toBearerToken(token));

        call.enqueue(new Callback<List<PartCategory>>() {
            @Override
            public void onResponse(@NotNull Call<List<PartCategory>> call, @NotNull Response<List<PartCategory>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get car brands success");
                    AppSession.getInstance().setPartCategories(response.body());
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PartCategory>> call, @NotNull Throwable t) {
                Log.e("REQUEST", "Get car brands failed");
                t.printStackTrace();
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getPart(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<Part>> call = partApi.getPart(AuthUtil.toBearerToken(token));

        call.enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(@NotNull Call<List<Part>> call, @NotNull Response<List<Part>> response) {
                if (response.isSuccessful()) {
                    AppSession.getInstance().setPart(response.body());
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Part>> call, @NotNull Throwable t) {
                Log.e("REQUEST", "Get car models failed");
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