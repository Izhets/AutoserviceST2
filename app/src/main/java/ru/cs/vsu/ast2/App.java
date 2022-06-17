package ru.cs.vsu.ast2;

import android.app.Application;
import android.os.StrictMode;
import ru.cs.vsu.ast2.api.AST2Service;

public class App extends Application {

    private static final AST2Service ast2Service = new AST2Service();


    public static AST2Service getAST2Service() {
        return ast2Service;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

}
