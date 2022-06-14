package ru.cs.vsu.ast2;

import android.app.Application;
import ru.cs.vsu.ast2.api.AST2Service;

public class App extends Application {

    private static final AST2Service ast2Service = new AST2Service();

    public static AST2Service getAST2Service() {
        return ast2Service;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
