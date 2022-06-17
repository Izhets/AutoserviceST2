package ru.cs.vsu.ast2.api.account;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.account.dto.Account;
import ru.cs.vsu.ast2.api.account.dto.AccountUpdate;
import ru.cs.vsu.ast2.api.account.dto.ReplenishBalance;
import ru.cs.vsu.ast2.util.AuthUtil;

import java.util.concurrent.Callable;

@Getter
@Setter
public class AccountRequests {

    private final AccountApi accountApi = App.getAST2Service().getAccountApi();
    private Account accountInfo;
    private AccountUpdate accountUpdated;

    public void getAccountInfo(String token, Callable<Void> onSuccess, Callable<Void> onFailure) {
        Call<Account> call = accountApi.getAccount(AuthUtil.toBearerToken(token));
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Log.d("REQUEST", "Get account was successful");
                    setAccountInfo(response.body());
                    AppSession.getInstance().setUserProfile(response.body());
                    try {
                        onSuccess.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Log.e("REQUEST", "Get account failure with code = " + response.code());
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Log.e("REQUEST", "Get account failure");
                t.printStackTrace();
                try {
                    onFailure.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateAccountInfo(String token, String name, String surname, String password, String passwordRepeat, Context applicationContext) {
        AccountUpdate accountUpdate = new AccountUpdate(passwordRepeat, name, password, surname);
        Call<Void> call = accountApi.updateAccount(AuthUtil.toBearerToken(token), accountUpdate);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext, "Данные успешно обновленны", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(applicationContext, "Ошибка при обновления данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(applicationContext, "Ошибка при обновления данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void replenishBalanceAccount(String token, String paymentType, Integer value, Context applicationContext) {
        ReplenishBalance replenishBalance = new ReplenishBalance("CARD", value);
        Call<Void> call = accountApi.replenishBalanceAccount(AuthUtil.toBearerToken(token), replenishBalance);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext, "Счёт успешно пополнен!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(applicationContext, "Ошибка при пополнении счёта", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(applicationContext, "Ошибка при пополнении счёта", Toast.LENGTH_SHORT).show();
            }
        });
    }

}