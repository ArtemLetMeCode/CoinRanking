package com.test.coinranking.di.module;

import com.test.coinranking.ui.coin_list.CoinListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract CoinListFragment currencyListFragment();
}
