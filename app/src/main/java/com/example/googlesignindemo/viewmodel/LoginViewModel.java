package com.example.googlesignindemo.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.example.googlesignindemo.view.ProductActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;

public class LoginViewModel extends ViewModel {
    private static final int RC_SIGN_IN = 0;

    public void onSignInClick(Activity activity, GoogleSignInClient mGoogleSignInClient) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, Intent data, Context context) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task, context);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task, Context context) {
        GoogleSignInAccount account = task.getResult();
        if (account == null) {
            return;
        }

        Intent intentProduct = new Intent(context, ProductActivity.class);
        context.startActivity(intentProduct);
    }

    public void ignoreSignIn(Context context) {
        Intent intentProduct = new Intent(context, ProductActivity.class);
        intentProduct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intentProduct);
    }
}
