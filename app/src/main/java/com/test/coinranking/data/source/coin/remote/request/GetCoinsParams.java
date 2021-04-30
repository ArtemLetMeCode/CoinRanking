package com.test.coinranking.data.source.coin.remote.request;

import java.util.HashMap;

public class GetCoinsParams {

    private String orderBy;
    private String orderDirection;
    private String limit;
    private String offset;

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    private GetCoinsParams(Builder builder) {
        setOrderBy(builder.orderBy.value);
        setOrderDirection(builder.orderDirection.value);
        setLimit(String.valueOf(builder.limit));
        setOffset(String.valueOf(builder.offset));
    }

    public HashMap<String, String> queryMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderBy", orderBy);
        map.put("orderDirection", orderDirection);
        map.put("limit", limit);
        map.put("offset", offset);
        return map;
    }


    public static final class Builder {

        private OrderBy orderBy;
        private OrderDirection orderDirection;
        private int limit;
        private int offset;

        public Builder() {
        }

        public Builder orderBy(OrderBy val) {
            orderBy = val;
            return this;
        }

        public Builder orderDirection(OrderDirection val) {
            orderDirection = val;
            return this;
        }

        public Builder limit(int val) {
            limit = val;
            return this;
        }

        public Builder offset(int val) {
            offset = val;
            return this;
        }

        public GetCoinsParams build() {
            return new GetCoinsParams(this);
        }
    }

    public enum OrderBy {

        PRICE("price"),
        MARKET_CUP("marketCap"),
        TWENTY_FOUR_H_VOLUME("24hVolume");

        private final String value;

        OrderBy(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OrderDirection {

        DESC("desc"),
        ASC("asc");

        private final String value;

        OrderDirection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        
        public static OrderDirection getOpposite(OrderDirection value){
            if (value == DESC)
                return ASC;
            else 
                return DESC;
        }
    }
}
