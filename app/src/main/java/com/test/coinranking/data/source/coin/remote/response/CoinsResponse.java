package com.test.coinranking.data.source.coin.remote.response;

import com.google.gson.annotations.SerializedName;
import com.test.coinranking.data.model.Coin;
import com.test.coinranking.data.model.Stats;

import java.util.ArrayList;

public class CoinsResponse {

    private String status;
    private CoinsData data;

    public String getStatus() {
        return status;
    }

    public CoinsData getData() {
        return data;
    }

    public class CoinsData {

        private Stats stats;
        private ArrayList<Coin> coins;

        public Stats getStats() {
            return stats;
        }

        public ArrayList<Coin> getCoins() {
            return coins;
        }
    }
}


