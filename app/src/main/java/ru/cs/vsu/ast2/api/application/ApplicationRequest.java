package ru.cs.vsu.ast2.api.application;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.application.dto.Application;
import ru.cs.vsu.ast2.util.AuthUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ApplicationRequest {

    private static final ApplicationApi applicationApi = App.getAST2Service().getApplicationApi();

    public ApplicationRequest() {
    }

    public void getUserApplications(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<List<Application>> call = applicationApi.getUserApplications(AuthUtil.toBearerToken(token));
        call.enqueue(new Callback<List<Application>>() {
            @Override
            public void onResponse(Call<List<Application>> call, Response<List<Application>> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get user cars success");
                    AppSession.getInstance().setUserApplications(response.body());
                    AppSession.getInstance().setApplicationIdMap(response.body().stream()
                            .collect(Collectors.toMap(Application::getId, Function.identity())));
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
            public void onFailure(Call<List<Application>> call, Throwable t) {
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

    public void addApplication(String token, Application application) {
        Call<Application> call = applicationApi.addUserApplication(AuthUtil.toBearerToken(token), application);

        call.enqueue(new Callback<Application>() {
            @Override
            public void onResponse(Call<Application> call, Response<Application> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Post user car success");
                }
            }

            @Override
            public void onFailure(Call<Application> call, Throwable t) {
                Log.e("REQUEST", "Post user car failure");
                t.printStackTrace();
            }
        });
    }

    public void deleteApplication(String token, UUID applicationId, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<Void> call = applicationApi.deleteUserApplication(AuthUtil.toBearerToken(token), applicationId);

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

    public void updateApplication(String token, UUID applicationId, Application application) {
        Call<Application> call = applicationApi.updateUserApplication(AuthUtil.toBearerToken(token), applicationId, application);

        call.enqueue(new Callback<Application>() {
            @Override
            public void onResponse(Call<Application> call, Response<Application> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Put user car success");
                }
            }

            @Override
            public void onFailure(Call<Application> call, Throwable t) {
                Log.e("REQUEST", "Put user car failure");
                t.printStackTrace();
            }
        });
    }

}