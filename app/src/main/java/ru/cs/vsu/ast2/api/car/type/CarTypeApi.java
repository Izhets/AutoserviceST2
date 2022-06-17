package ru.cs.vsu.ast2.api.car.type;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.cs.vsu.ast2.api.car.type.dto.CarType;

import java.util.List;
import java.util.UUID;

public interface CarTypeApi {

    @GET("/car-types")
    Call<List<CarType>> getCarTypes();

    @GET("/car-types/{id}")
    Call<CarType> getCarType(@Path("id") UUID id);

}
