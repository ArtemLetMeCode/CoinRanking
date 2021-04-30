package com.test.coinranking.base.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.lifecycle.LiveData;

import com.test.coinranking.R;

import javax.inject.Inject;

public class ConnectionHelper extends LiveData<Boolean> {

    private ConnectivityManager connectivityManager;
    private  String message;

    @Inject
    public ConnectionHelper(Application application) {
        this.connectivityManager =  (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.message = application.getString(R.string.internet_connection);
    }

    public Boolean isOnline() {
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = connectivityManager.getActiveNetwork();
                if (n != null) {
                    final NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(n);
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }
        return false;
    }

    public String getMessage() {
        return message;
    }
}
