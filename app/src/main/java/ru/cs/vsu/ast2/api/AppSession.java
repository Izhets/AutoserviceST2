package ru.cs.vsu.ast2.api;

import android.content.Context;
import android.content.SharedPreferences;
import lombok.Getter;
import lombok.Setter;
import ru.cs.vsu.ast2.App;
import ru.cs.vsu.ast2.api.account.AccountRequests;
import ru.cs.vsu.ast2.api.account.dto.Account;
import ru.cs.vsu.ast2.api.news.NewsRequests;
import ru.cs.vsu.ast2.api.news.dto.News;
import ru.cs.vsu.ast2.ui.logged.LoggedMainActivity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class AppSession {

    private static final String USER_DATA = "UserData";
    private static final String TOKEN = "Token";
    private static final String USER_ID = "UserId";
    private static volatile AppSession appSession;

    private List<News> news;
    private Map<UUID, News> newsIdMap;

    private Account userProfile;
    private Context context;

    public static AppSession getInstance() {
        if (appSession == null)
            synchronized (AppSession.class) {
                if (appSession == null) {
                    appSession = new AppSession();
                }
            }
        return appSession;
    }

    public static void saveUserData(String token, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static boolean isAnon(Context context) {
        return getToken(context).trim().isEmpty();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, "");
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, "");
    }

    public static void clearUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void collectAuthData(Context context) {
        AccountRequests accountRequests = new AccountRequests();
        accountRequests.getAccountInfo(getToken(context), () -> null, () -> null);
    }

    public void collectNews() {
        NewsRequests newsRequests = new NewsRequests();
        newsRequests.getNews(() -> null, () -> null);
    }

}
