package com.test.coinranking.ui.common;

import androidx.lifecycle.ViewModel;

import com.test.coinranking.base.ApiError;
import com.test.coinranking.base.RxWrapper;
import com.test.coinranking.base.SingleLiveEvent;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class BaseViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected final SingleLiveEvent<Boolean> lvProgress = new SingleLiveEvent<>();
    protected SingleLiveEvent<String> lvMessage = new SingleLiveEvent<>();
    protected final SingleLiveEvent<ApiError> lvApiError = new SingleLiveEvent<>();

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
    public SingleLiveEvent<Boolean> lvProgress() {
        return lvProgress;
    }
    public SingleLiveEvent<String> lvMessage() {
        return lvMessage;
    }
    public SingleLiveEvent<ApiError> lvApiError() {
        return lvApiError;
    }

    @Inject
    public BaseViewModel() {
    }

    public <T> void addDisposable(Observable<T> observable, Consumer<T> callback) {
        compositeDisposable.add(RxWrapper.compositeWrapper(observable).doOnTerminate(terminateAction()).doOnSubscribe(loadingState()).subscribe(callback, errorConsumer()));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    private Action terminateAction() {
        return () -> lvProgress.setValue(false);
    }

    private Consumer<Disposable> loadingState() {
        return disposable -> lvProgress.setValue(true);
    }

    private Consumer<Throwable> errorConsumer() {
        return throwable -> {
//            lvApiError.postValue(ApiError.build(throwable));
            lvProgress.postValue(false);
        };
    }
}
