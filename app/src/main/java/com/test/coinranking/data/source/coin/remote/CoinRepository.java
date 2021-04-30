package com.test.coinranking.data.source.coin.remote;

import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;
import com.test.coinranking.data.source.coin.remote.response.CoinsResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class CoinRepository {

    private final CoinRemoteStore remoteStore;

    @Inject
    public CoinRepository(CoinRemoteStore remoteStore) {
        this.remoteStore = remoteStore;
    }

    public Observable<CoinsResponse> getCoins(GetCoinsParams params){
        return remoteStore.getCoins(params);
    }
}
