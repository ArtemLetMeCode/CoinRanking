package com.test.coinranking.data.source.coin.remote;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.test.coinranking.data.model.Coin;
import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;

import io.reactivex.disposables.CompositeDisposable;

public class CoinDataSourceFactory extends DataSource.Factory<Integer, Coin> {

    private CompositeDisposable compositeDisposable;
    private CoinRepository repository;
    private GetCoinsParams.OrderBy orderBy;
    private GetCoinsParams.OrderDirection orderDirection;
    private MutableLiveData<String> requestFailureLiveData;

    CoinPagingDataSource dataSource;

    public CoinPagingDataSource getDataSource() {
        return dataSource;
    }

    public CoinDataSourceFactory(CompositeDisposable compositeDisposable, CoinRepository repository, GetCoinsParams.OrderBy orderBy, GetCoinsParams.OrderDirection orderDirection, MutableLiveData<String> requestFailureLiveData) {
        this.compositeDisposable = compositeDisposable;
        this.repository = repository;
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
        this.requestFailureLiveData = requestFailureLiveData;
    }

    public void setOrderBy(GetCoinsParams.OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrderDirection(GetCoinsParams.OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
    }

    MutableLiveData<CoinPagingDataSource> lvDataSource = new MutableLiveData<CoinPagingDataSource>();

    @NonNull
    @Override
    public DataSource<Integer, Coin> create() {

        dataSource = new CoinPagingDataSource(compositeDisposable, repository, orderBy, orderDirection, requestFailureLiveData);
        lvDataSource.postValue(dataSource);
        return dataSource;
    }
}
