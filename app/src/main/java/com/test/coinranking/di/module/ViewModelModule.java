package com.test.coinranking.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.test.coinranking.base.AppViewModelFactory;
import com.test.coinranking.di.annotation.ViewModelKey;
import com.test.coinranking.ui.coin_list.CurrencyListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel.class)
    abstract ViewModel bindCurrencyListViewModel(CurrencyListViewModel currencyListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
