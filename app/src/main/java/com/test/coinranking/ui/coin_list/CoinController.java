package com.test.coinranking.ui.coin_list;

import android.content.Context;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.airbnb.epoxy.paging.PagedListEpoxyController;
import com.test.coinranking.data.model.Coin;
import com.test.coinranking.ui.coin_list.model.CoinListModel_;
import com.test.coinranking.ui.coin_list.model.HeaderModel;
import com.test.coinranking.ui.coin_list.model.HeaderModel_;
import com.test.coinranking.ui.coin_list.model.LoaderModel_;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CoinController extends PagedListEpoxyController<Coin> {


    private HeaderModel.HeaderListener listener;
    private Context context;

    public CoinController( Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public EpoxyModel<?> buildItemModel(int i, @Nullable Coin coin) {
        if (coin != null){
            return new CoinListModel_()
                    .id(coin.getUuid())
                    .coin(coin)
                    .context(context);
        } else {
            return new LoaderModel_()
                    .id("loader");
        }
    }
}
