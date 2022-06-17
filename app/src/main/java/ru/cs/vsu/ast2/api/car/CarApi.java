package ru.cs.vsu.ast2.api.car;

import retrofit2.Call;
import retrofit2.http.*;
import ru.cs.vsu.ast2.api.car.dto.Car;

import java.util.List;
import java.util.UUID;

public interface CarApi {

    @GET("/cars")
    Call<List<Car>> getUserCars(@Header("Authorization") String token);

    @POST("/cars")
    Call<Car> addUserCar(@Header("Authorization") String token, @Body Car car);

    @DELETE("/cars/{id}")
    Call<Void> deleteUserCar(@Header("Authorization") String token, @Path("id")UUID id);

    @GET("/cars/{id}")
    Call<Car> getUserCarById(@Header("Authorization") String token, @Path("id")UUID id);

    @PUT("/cars/{id}")
    Call<Car> updateUserCar(@Header("Authorization") String token, @Path("id") UUID id, @Body Car car);

}
