package com.test.coinranking.ui.coin_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.test.coinranking.data.model.Coin;
import com.test.coinranking.data.source.coin.remote.CoinDataSourceFactory;
import com.test.coinranking.data.source.coin.remote.CoinRepository;
import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;
import com.test.coinranking.data.source.coin.remote.response.CoinsResponse;
import com.test.coinranking.ui.common.BaseViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CurrencyListViewModel extends BaseViewModel {

    private final CoinRepository repository;
    private CoinDataSourceFactory coinDataSourceFactory;

    private LiveData<PagedList<Coin>> lvCoins = new MutableLiveData<>();
    private  MutableLiveData<String> requestFailureLiveData = new MutableLiveData<>();
    private final MutableLiveData<GetCoinsParams.OrderBy> lvOrderBy = new MutableLiveData<>(GetCoinsParams.OrderBy.MARKET_CUP);
    private final MutableLiveData<GetCoinsParams.OrderDirection> lvOrderDirection = new MutableLiveData<>(GetCoinsParams.OrderDirection.DESC);

    LiveData<PagedList<Coin>> lvCoins() {
        return lvCoins;
    }

    MutableLiveData<GetCoinsParams.OrderBy> orderBy() {
        return lvOrderBy;
    }

    MutableLiveData<GetCoinsParams.OrderDirection> orderDirection() {
        return lvOrderDirection;
    }

    MutableLiveData<String> requestFailureLiveData() {
        return requestFailureLiveData;
    }


    @Inject
    public CurrencyListViewModel(CoinRepository repository) {
        this.repository = repository;

        this.coinDataSourceFactory = new CoinDataSourceFactory(getCompositeDisposable(), repository, lvOrderBy.getValue(), lvOrderDirection.getValue(), requestFailureLiveData);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .build();

        lvCoins = new LivePagedListBuilder(this.coinDataSourceFactory, config).build();
    }

    private void reload() {
        coinDataSourceFactory.getDataSource().invalidate();

    }

    public void setOrderBy(GetCoinsParams.OrderBy value) {
        lvOrderBy.postValue(value);
        lvOrderDirection.postValue(GetCoinsParams.OrderDirection.DESC);
        coinDataSourceFactory.setOrderBy(value);
        reload();
    }

    public void setOppositeOrderDirection() {
        lvOrderDirection.postValue(GetCoinsParams.OrderDirection.getOpposite(lvOrderDirection.getValue()));
        coinDataSourceFactory.setOrderDirection(lvOrderDirection.getValue());
        reload();
    }




}
