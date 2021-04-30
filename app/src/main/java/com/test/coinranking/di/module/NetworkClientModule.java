package com.test.coinranking.di.module;

import android.app.Application;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.test.coinranking.BuildConfig;
import com.test.coinranking.base.util.ConnectionHelper;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkClientModule {

    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String API_VERSION = "2";

    @Singleton
    @Provides
    static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor.Level level = (BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
        return new HttpLoggingInterceptor()
                .setLevel(level);
    }

    @Singleton
    @Provides
    static ChuckInterceptor provideChuckInterceptor(Application application) {
        return new ChuckInterceptor(application);
    }

    @Singleton
    @Provides
    static NetworkConnectionInterceptor provideNetworkConnectionInterceptor(ConnectionHelper connectionHelper) {
        return new NetworkConnectionInterceptor(connectionHelper);
    }


    @Singleton
    @Provides
    static OkHttpClient provideHttpClient(HttpLoggingInterceptor logging, NetworkConnectionInterceptor networkConnectionInterceptor, ChuckInterceptor chuckInterceptor) {

        long TIMEOUT_SEC = 60;
        Interceptor headerInterceptor = chain -> {
            Request request = chain.request();
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json; charset=utf-8");
            builder.addHeader("X-App-Platform", "android");
            builder.addHeader("X-Access-Token", BuildConfig.TOKEN);
            return chain.proceed(request);
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckInterceptor);
        }
        builder.hostnameVerifier((hostname, session) -> true);
        return builder.build();
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                        return expose != null && !expose.serialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .setLenient()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
    }


    @Singleton
    @Provides
    static GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory
            gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
