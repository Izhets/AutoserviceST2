package ru.cs.vsu.ast2.api.car.type;

import android.util.Log;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.car.type.dto.CarType;

import java.util.List;
import java.util.concurrent.Callable;

public final class CarTypeRequest {

    private static final CarTypeApi carTypeApi = App.getAST2Service().getCarTypeApi();

    public CarTypeRequest() {
    }

    public void getCarTypes(Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<CarType>> call = carTypeApi.getCarTypes();

        call.enqueue(new Callback<List<CarType>>() {
            @Override
            public void onResponse(@NotNull Call<List<CarType>> call, @NotNull Response<List<CarType>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get car types success");
                    AppSession.getInstance().setCarTypes(response.body());
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
            public void onFailure(@NotNull Call<List<CarType>> call, @NotNull Throwable t) {
                Log.e("REQUEST", "Get car types failed");
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
