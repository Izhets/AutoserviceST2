package ru.cs.vsu.ast2.api.car;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.car.dto.Car;
import ru.cs.vsu.ast2.util.AuthUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CarRequest {

    private static final CarApi carApi = App.getAST2Service().getCarApi();

    public CarRequest() {
    }

    public void getUserCars(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<Car>> call = carApi.getUserCars(AuthUtil.toBearerToken(token));
        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get user cars success");
                    AppSession.getInstance().setUserCars(response.body());
                    AppSession.getInstance().setCarIdMap(response.body().stream()
                            .collect(Collectors.toMap(Car::getId, Function.identity())));
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Log.e("REQUEST", "Get user cars failure with code = " + response.code());
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.e("REQUEST", "Get user cars id failure");
                t.printStackTrace();
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addCar(String token, Car car) {
        Call<Car> call = carApi.addUserCar(AuthUtil.toBearerToken(token), car);

        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Post user car success");
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.e("REQUEST", "Post user car failure");
                t.printStackTrace();
            }
        });
    }

    public void deleteCar(String token, UUID carId, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<Void> call = carApi.deleteUserCar(AuthUtil.toBearerToken(token), carId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Delete user car success");
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("REQUEST", "Delete user car failure with code = " + response.code());
                    try {
                        onFailure.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("REQUEST", "Delete user car id failure");
                t.printStackTrace();
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void updateCar(String token, UUID carId, Car car) {
        Call<Car> call = carApi.updateUserCar(AuthUtil.toBearerToken(token), carId, car);

        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Put user car success");
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.e("REQUEST", "Put user car failure");
                t.printStackTrace();
            }
        });
    }

}
