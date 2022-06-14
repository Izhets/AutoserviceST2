package ru.cs.vsu.ast2.api.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.cs.vsu.ast2.api.auth.login.dto.LoginRequest;
import ru.cs.vsu.ast2.api.auth.login.dto.LoginResponse;
import ru.cs.vsu.ast2.api.auth.register.dto.RegisterRequest;

public interface AuthApi {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<Void> register(@Body RegisterRequest registerRequest);
}

