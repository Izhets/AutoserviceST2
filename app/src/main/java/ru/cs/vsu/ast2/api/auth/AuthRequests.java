package ru.cs.vsu.ast2.api.auth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.auth.login.dto.LoginRequest;
import ru.cs.vsu.ast2.api.auth.login.dto.LoginResponse;
import ru.cs.vsu.ast2.api.auth.register.dto.RegisterRequest;
import ru.cs.vsu.ast2.ui.registration.RegistrationActivity;

import java.util.concurrent.Callable;

public final class AuthRequests {

    private AuthRequests() {}

    public static void login(String login, String password, Context context, Callable<Void> onSucces, Callable<Void> onFailure) {
        LoginRequest loginRequest = new LoginRequest(login, password);
        Call<LoginResponse> call = App.getAST2Service().getAuthApi().login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "LOGIN REQUEST SUCCESSFUL\nLogin user token: " + response.body().getToken());
                    Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show();
                    //AppSession.saveUserData(response.body().getToken(), context);
                    //System.out.println(AppSession.getToken(context));

                    try {
                        onSucces.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("REQUEST", "Login request failure");
                    Toast.makeText(context, "Ошибка входа", Toast.LENGTH_SHORT).show();

                    try {
                        onFailure.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Ошибка в данных");
            }
        });
    }

    public static void register(String name, String surname, String phoneNumber, String email,
                                String password, String passwordRepeat, RegistrationActivity applicationContext) {
        RegisterRequest register = new RegisterRequest(passwordRepeat, email, name, password, phoneNumber, surname);
        Call<Void> call = App.getAST2Service().getAuthApi().register(register);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
                    System.out.println("Пользователь зарегистрирован");
                } else {
                    Toast.makeText(applicationContext, "Ошибка в данных", Toast.LENGTH_SHORT).show();
                    System.out.println("Ошибка в данных");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(applicationContext, "Ошибка в данных", Toast.LENGTH_SHORT).show();
                System.out.println("Ошибка в данных");
            }
        });
    }

}
