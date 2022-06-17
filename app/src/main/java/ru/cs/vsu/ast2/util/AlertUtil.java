package ru.cs.vsu.ast2.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

public final class AlertUtil {

    public static void alertDialog(View root, String title, String message) {
        new AlertDialog.Builder(root.getContext())
                .setTitle(title)
                .setMessage(message)
                .create()
                .show();
    }

    public static void alertDialog(View root, String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(root.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .create()
                .show();
    }

    public static void alertDialog(View root, String title, String message,
                                   DialogInterface.OnClickListener yesListener,
                                   DialogInterface.OnClickListener noListener) {
        new AlertDialog.Builder(root.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, yesListener)
                .setNegativeButton(android.R.string.no, noListener)
                .create()
                .show();
    }

}
