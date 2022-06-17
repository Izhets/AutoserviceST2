package ru.cs.vsu.ast2.api.car.brand;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import ru.cs.vsu.ast2.api.car.brand.dto.CarBrand;
import ru.cs.vsu.ast2.api.car.brand.dto.CarModel;

import java.util.List;
import java.util.UUID;

public interface CarBrandApi {

    @GET("/car-models")
    Call<List<CarModel>> getCarModel(@Header("Authorization") String token);

    @GET("/car-brands")
    Call<List<CarBrand>> getCarBrands(@Header("Authorization") String token);

    @GET("/car-brands/{id}")
    Call<CarBrand> getCarBrandById(@Header("Authorization") String token, @Path("id") UUID id);

    @GET("/car-brands/{brandId}/models")
    Call<List<CarModel>> getCarModels(@Header("Authorization") String token, @Path("brandId") UUID brandId);

    @GET("/car-brands/{brandId}/models/{id}")
    Call<CarModel> getCarModelById(@Header("Authorization") String token, @Path("brandId") UUID brandId, @Path("id") UUID id);

}
