package ru.cs.vsu.ast2.api.application;

import retrofit2.Call;
import retrofit2.http.*;
import ru.cs.vsu.ast2.api.application.dto.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationApi {

    @GET("/applications")
    Call<List<Application>> getUserApplications(@Header("Authorization") String token);

    @POST("/applications")
    Call<Application> addUserApplication(@Header("Authorization") String token, @Body Application application);

    @DELETE("/applications/{id}")
    Call<Void> deleteUserApplication(@Header("Authorization") String token, @Path("id") UUID id);

    @GET("/applications/{id}")
    Call<Application> getUserApplicationById(@Header("Authorization") String token, @Path("id")UUID id);

    @PUT("/applications/{id}")
    Call<Application> updateUserApplication(@Header("Authorization") String token, @Path("id") UUID id, @Body Application application);

}