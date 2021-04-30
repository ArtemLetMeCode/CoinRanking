package com.test.coinranking.ui.coin_list.model;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.test.coinranking.R;
import com.test.coinranking.data.model.Coin;
import com.test.coinranking.databinding.ItemEpoxyHeaderModelBinding;
import com.test.coinranking.databinding.ItemEpoxyModelCoinListBinding;

@EpoxyModelClass(layout = R.layout.item_epoxy_header_model)
public abstract class HeaderModel extends EpoxyModelWithHolder<HeaderModel.HeaderHolder> {

    @EpoxyAttribute
    HeaderListener listener;

    @Override
    public void bind(@NonNull HeaderModel.HeaderHolder holder) {
        super.bind(holder);

        holder.binding.marketCap.setOnClickListener(v -> {
            if (listener != null)
                listener.onMarketCapClick();
        });

        holder.binding.price.setOnClickListener(v -> {
            if (listener != null)
                listener.onPriceClick();
        });

        holder.binding.twentyFourHVolume.setOnClickListener(v -> {
            if (listener != null)
                listener.on24HClick();
        });
    }

    class HeaderHolder extends EpoxyHolder {
        ItemEpoxyHeaderModelBinding binding;

        @Override
        protected void bindView(@NonNull View itemView) {
            binding = ItemEpoxyHeaderModelBinding.bind(itemView);
        }
    }

    public interface HeaderListener {

        void onMarketCapClick();

        void onPriceClick();

        void on24HClick();
    }
}
