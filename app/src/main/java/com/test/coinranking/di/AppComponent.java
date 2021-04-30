package com.test.coinranking.di;

import android.app.Application;

import com.test.coinranking.CoinRankingApplication;
import com.test.coinranking.di.module.ActivityModule;
import com.test.coinranking.di.module.FragmentModule;
import com.test.coinranking.di.module.NetworkApiModule;
import com.test.coinranking.di.module.NetworkClientModule;
import com.test.coinranking.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        ViewModelModule.class,
        NetworkClientModule.class,
        NetworkApiModule.class
})
public interface AppComponent extends AndroidInjector<CoinRankingApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
