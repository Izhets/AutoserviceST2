package ru.cs.vsu.ast2.api.car.brand;

import android.util.Log;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.car.brand.dto.CarBrand;
import ru.cs.vsu.ast2.api.car.brand.dto.CarModel;
import ru.cs.vsu.ast2.util.AuthUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public final class CarBrandRequest {

    private static final CarBrandApi carBrandApi = App.getAST2Service().getCarBrandApi();

    public CarBrandRequest() {
    }

    public void getCarBrands(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<CarBrand>> call = carBrandApi.getCarBrands(AuthUtil.toBearerToken(token));

        call.enqueue(new Callback<List<CarBrand>>() {
            @Override
            public void onResponse(@NotNull Call<List<CarBrand>> call, @NotNull Response<List<CarBrand>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get car brands success");
                    AppSession.getInstance().setCarBrands(response.body());
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
            public void onFailure(@NotNull Call<List<CarBrand>> call, @NotNull Throwable t) {
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

    public static void getCarModels(String token, UUID brandId) {
        Call<List<CarModel>> call = carBrandApi.getCarModels(token, brandId);

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<CarModel>> call, @NotNull Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get car models success");
                    AppSession.getInstance().setCarModels(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CarModel>> call, @NotNull Throwable t) {
                Log.e("REQUEST", "Get car models failed");
                t.printStackTrace();
            }

        });
    }

    public void getCarModel(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<CarModel>> call = carBrandApi.getCarModel(AuthUtil.toBearerToken(token));

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<CarModel>> call, @NotNull Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    AppSession.getInstance().setCarModels(response.body());
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
            public void onFailure(@NotNull Call<List<CarModel>> call, @NotNull Throwable t) {
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
