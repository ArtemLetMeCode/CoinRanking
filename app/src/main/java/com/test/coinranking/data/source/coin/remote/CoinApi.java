package com.test.coinranking.data.source.coin.remote;

import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;
import com.test.coinranking.data.source.coin.remote.response.CoinsResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

public interface CoinApi {

    @GET("v2/coins")
    Observable<CoinsResponse> getCoins(@QueryMap HashMap<String, String> params);
}
