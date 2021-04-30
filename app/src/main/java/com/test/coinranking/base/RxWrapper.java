package com.test.coinranking.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxWrapper {
    public static  <T> Observable<T> compositeWrapper(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
