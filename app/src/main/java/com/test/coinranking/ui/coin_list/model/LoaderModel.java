package com.test.coinranking.ui.coin_list.model;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.test.coinranking.R;
import com.test.coinranking.databinding.ItemEpoxyHeaderModelBinding;
import com.test.coinranking.databinding.ItemEpoxyLoaderModelBinding;

@EpoxyModelClass(layout = R.layout.item_epoxy_loader_model)
public abstract class LoaderModel extends EpoxyModelWithHolder<LoaderModel.LoaderHolder> {


    class LoaderHolder extends EpoxyHolder {
        ItemEpoxyLoaderModelBinding binding;

        @Override
        protected void bindView(@NonNull View itemView) {
            binding = ItemEpoxyLoaderModelBinding.bind(itemView);
        }
    }
}
