package com.test.coinranking.di.module;

import com.test.coinranking.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
