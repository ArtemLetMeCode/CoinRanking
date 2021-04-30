package com.test.coinranking.data.source.coin.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.test.coinranking.base.RxWrapper;
import com.test.coinranking.data.State;
import com.test.coinranking.data.model.Coin;
import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;
import com.test.coinranking.data.source.coin.remote.response.CoinsResponse;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class CoinPagingDataSource extends PageKeyedDataSource<Integer, Coin> {

    private final int PAGE_SIZE = 20;

    private CompositeDisposable compositeDisposable;
    private CoinRepository repository;
    private GetCoinsParams.OrderBy orderBy;
    private GetCoinsParams.OrderDirection orderDirection;
    private MutableLiveData<String> requestFailureLiveData;

    private int offset;

    MutableLiveData<State> state = new MutableLiveData<State>();

    public CoinPagingDataSource(CompositeDisposable compositeDisposable, CoinRepository repository, GetCoinsParams.OrderBy orderBy, GetCoinsParams.OrderDirection orderDirection, MutableLiveData<String> requestFailureLiveData) {
        this.compositeDisposable = compositeDisposable;
        this.repository = repository;
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
        this.requestFailureLiveData = requestFailureLiveData;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Coin> callback) {

        GetCoinsParams getCoinsParams = new GetCoinsParams.Builder()
                .limit(PAGE_SIZE)
                .offset(0)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();

        compositeDisposable.add(RxWrapper.compositeWrapper(repository.getCoins(getCoinsParams)).doOnTerminate(terminateAction()).doOnSubscribe(loadingState())
                .subscribe(
                        new Consumer<CoinsResponse>() {
                            @Override
                            public void accept(CoinsResponse coinsResponse) throws Exception {

                                offset = coinsResponse.getData().getCoins().size();
                                callback.onResult(coinsResponse.getData().getCoins(), 0, offset);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                updateState(State.ERROR);
                                requestFailureLiveData.postValue(((HttpException) throwable).response().errorBody().string());
                            }
                        }
                ));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Coin> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Coin> callback) {

        GetCoinsParams getCoinsParams = new GetCoinsParams.Builder()
                .limit(PAGE_SIZE)
                .offset(offset)
                .orderBy(GetCoinsParams.OrderBy.MARKET_CUP)
                .orderDirection(GetCoinsParams.OrderDirection.DESC)
                .build();

        compositeDisposable.add(RxWrapper.compositeWrapper(repository.getCoins(getCoinsParams)).doOnTerminate(terminateAction()).doOnSubscribe(loadingState())
                .subscribe(
                        new Consumer<CoinsResponse>() {
                            @Override
                            public void accept(CoinsResponse coinsResponse) throws Exception {

                                offset = offset + coinsResponse.getData().getCoins().size();
                                callback.onResult(coinsResponse.getData().getCoins(), offset);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                updateState(State.ERROR);
                                requestFailureLiveData.postValue(((HttpException) throwable).response().errorBody().string());
                            }
                        }
                ));
    }

    private void updateState(State state) {
        this.state.postValue(state);
    }

    private Action terminateAction() {
        return new Action() {
            @Override
            public void run() throws Exception {
                updateState(State.DONE);
            }
        };
    }

    private Consumer<Disposable> loadingState() {
        return response -> updateState(State.LOADING);
    }

}
