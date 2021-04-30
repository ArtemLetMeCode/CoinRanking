package com.test.coinranking.di.module;

import com.test.coinranking.base.NoConnectivityException;
import com.test.coinranking.base.util.ConnectionHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {
    private  final ConnectionHelper connectionHelper;

    public NetworkConnectionInterceptor(ConnectionHelper connectionHelper) {
        this.connectionHelper = connectionHelper;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (!connectionHelper.isOnline()) {
            throw new NoConnectivityException(connectionHelper.getMessage());
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}
