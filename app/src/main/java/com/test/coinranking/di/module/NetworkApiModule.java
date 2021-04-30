package com.test.coinranking.di.module;

import com.test.coinranking.data.source.coin.remote.CoinApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetworkApiModule {

    @Provides
    static CoinApi provideCoinApi(Retrofit retrofit) {
        return retrofit.create(CoinApi.class);
    }
}
