package com.test.coinranking.data.source.coin.remote;

import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;
import com.test.coinranking.data.source.coin.remote.response.CoinsResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CoinRemoteStore {

    private final CoinApi api;

    @Inject
    public CoinRemoteStore(CoinApi api) {
        this.api = api;
    }

    public Observable<CoinsResponse> getCoins(GetCoinsParams params) {
        return api.getCoins(params.queryMap());
    }
}
