package com.test.coinranking.data.model;

public class Stats {

    public final int total;
    public final int totalMarkets;
    public final int totalExchanges;
    public final String totalMarketCap;
    public final String total24hVolume;

    public Stats(int total, int totalMarkets, int totalExchanges, String totalMarketCap, String total24hVolume) {
        this.total = total;
        this.totalMarkets = totalMarkets;
        this.totalExchanges = totalExchanges;
        this.totalMarketCap = totalMarketCap;
        this.total24hVolume = total24hVolume;
    }
}
