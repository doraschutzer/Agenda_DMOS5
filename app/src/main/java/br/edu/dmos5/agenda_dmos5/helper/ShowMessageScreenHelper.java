package br.edu.dmos5.agenda_dmos5.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class ShowMessageScreenHelper {
    public static void showSnackbar(String message, ConstraintLayout constraintLayout) {
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void showToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
