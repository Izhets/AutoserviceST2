package ru.cs.vsu.ast2.api.account;

import retrofit2.Call;
import retrofit2.http.*;
import ru.cs.vsu.ast2.api.account.dto.Account;
import ru.cs.vsu.ast2.api.account.dto.AccountUpdate;
import ru.cs.vsu.ast2.api.account.dto.ReplenishBalance;

public interface AccountApi {

    @GET("/account")
    Call<Account> getAccount(@Header("Authorization") String token);

    @PUT("/account")
    Call<Void> updateAccount(@Header("Authorization") String token, @Body AccountUpdate accountUpdate);

    @POST("/account/replenish-balance")
    Call<Void> replenishBalanceAccount(@Header("Authorization") String token, @Body ReplenishBalance replenishBalance);

}