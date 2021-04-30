package com.test.coinranking.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Coin {

    private final String uuid;
    private final String symbol;
    private final String name;
    private final String color;
    private final String iconUrl;
    private final String marketCup;
    private final String price;
    private final String listedAt;
    private final String tier;
    private final String change;
    private final String rank;
    private final ArrayList<String> sparkLine;
    private final Boolean lowVolume;
    private final String  coinrankingUrl;
    @SerializedName("24hVolume")
    private final String twentyFourHVolume;
    private final String  btcPrice;

    public Coin(String uuid, String symbol, String name, String color, String iconUrl, String marketCup, String price, String listedAt, String tier, String change, String rank, ArrayList<String> sparkLine, Boolean lowVolume, String coinrankingUrl, String twentyFourHVolume, String btcPrice) {
        this.uuid = uuid;
        this.symbol = symbol;
        this.name = name;
        this.color = color;
        this.iconUrl = iconUrl;
        this.marketCup = marketCup;
        this.price = price;
        this.listedAt = listedAt;
        this.tier = tier;
        this.change = change;
        this.rank = rank;
        this.sparkLine = sparkLine;
        this.lowVolume = lowVolume;
        this.coinrankingUrl = coinrankingUrl;
        this.twentyFourHVolume = twentyFourHVolume;
        this.btcPrice = btcPrice;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getMarketCup() {
        return marketCup;
    }

    public String getPrice() {
        return price;
    }

    public String getListedAt() {
        return listedAt;
    }

    public String getTier() {
        return tier;
    }

    public String getChange() {
        return change;
    }

    public String getRank() {
        return rank;
    }

    public ArrayList<String> getSparkLine() {
        return sparkLine;
    }

    public Boolean getLowVolume() {
        return lowVolume;
    }

    public String getCoinrankingUrl() {
        return coinrankingUrl;
    }

    public String getTwentyFourHVolume() {
        return twentyFourHVolume;
    }

    public String getBtcPrice() {
        return btcPrice;
    }
}
