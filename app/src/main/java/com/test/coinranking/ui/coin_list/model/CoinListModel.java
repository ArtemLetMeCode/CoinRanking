package com.test.coinranking.ui.coin_list.model;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.coinranking.R;
import com.test.coinranking.base.util.glide.CircleTransform;
import com.test.coinranking.base.util.glide.GlideApp;
import com.test.coinranking.base.util.glide.SvgSoftwareLayerSetter;
import com.test.coinranking.data.model.Coin;
import com.test.coinranking.databinding.ItemEpoxyModelCoinListBinding;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

@EpoxyModelClass(layout = R.layout.item_epoxy_model_coin_list)
public abstract class CoinListModel extends EpoxyModelWithHolder<CoinListModel.CoinListHolder> {

    @EpoxyAttribute
    Context context;
    @EpoxyAttribute
    Coin coin;

    @Override
    public void bind(@NonNull CoinListHolder holder) {
        super.bind(holder);

        if (coin != null) {
            holder.binding.rank.setText(coin.getRank());
            holder.binding.name.setText(coin.getName());
            holder.binding.symbol.setText(coin.getSymbol());
            holder.binding.price.setText(context.getString(R.string.currency_value_format, formatPrice(coin.getPrice())));
            holder.binding.twentyFourHVolume.setText(context.getString(R.string.currency_value_format, truncateNumber(coin.getTwentyFourHVolume())));
            holder.binding.change.setText(context.getString(R.string.percent_value_format, formatPercent(coin.getChange())));
            holder.binding.change.setTextColor(ContextCompat.getColor(context, getChangeColor(coin.getChange())));

            RequestBuilder<PictureDrawable> requestBuilder = GlideApp.with(context)
                    .as(PictureDrawable.class)
                    .transition(withCrossFade())
                    .listener(new SvgSoftwareLayerSetter());

            requestBuilder
                    .load(Uri.parse(coin.getIconUrl()))
                    .circleCrop()
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(holder.binding.icon);
        }
    }

    class CoinListHolder extends EpoxyHolder {
        ItemEpoxyModelCoinListBinding binding;

        @Override
        protected void bindView(@NonNull View itemView) {
            binding = ItemEpoxyModelCoinListBinding.bind(itemView);
        }
    }

    private String formatPrice(String value) {
        return new DecimalFormat("###,###.##").format(Float.valueOf(value));
    }

    private String truncateNumber(String value) {
        float million = 1000000f;
        float billion = 1000000000f;
        float trillion = 1000000000000f;

        float number = Float.valueOf(value);

        if (number == 0)
            return "0";

        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return new DecimalFormat("#.##").format(fraction) + " M";
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return new DecimalFormat("#.##").format(fraction) + " B";
        } else {
            float fraction = calculateFraction(number, trillion);
            return new DecimalFormat("#.##").format(fraction) + " T";
        }
    }

    private float calculateFraction(float number, float divisor) {
        return number / divisor;
    }

    private String formatPercent(String value) {
        if (value != null) {
            return new DecimalFormat("#.##").format(Float.valueOf(value));
        } else {
            return context.getString(R.string.no_data);
        }
    }

    private int getChangeColor(String value) {

        if (value == null)
            return R.color.white_75;

        int changeColor;

        if (value.contains("-"))
            changeColor = R.color.red;
        else
            changeColor = R.color.green;

        return changeColor;
    }
}
