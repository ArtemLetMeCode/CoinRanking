package com.test.coinranking.ui.coin_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import com.test.coinranking.R;
import com.test.coinranking.data.model.Coin;
import com.test.coinranking.data.source.coin.remote.request.GetCoinsParams;
import com.test.coinranking.databinding.FragmentCoinListBinding;
import com.test.coinranking.ui.coin_list.model.HeaderModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CoinListFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CurrencyListViewModel viewModel;

    FragmentCoinListBinding binding;
    private CoinController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCoinListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(CurrencyListViewModel.class);
        viewModel.lvCoins().observe(getViewLifecycleOwner(), observeCoins());
        viewModel.requestFailureLiveData().observe(getViewLifecycleOwner(), observeError());

        controller = new CoinController(getContext());
        binding.rvCoin.setController(controller);

        binding.marketCap.setOnClickListener(v -> {
            if (viewModel.orderBy().getValue() == GetCoinsParams.OrderBy.MARKET_CUP) {
                viewModel.setOppositeOrderDirection();
            } else {
                viewModel.setOrderBy(GetCoinsParams.OrderBy.MARKET_CUP);
            }

        });
        binding.price.setOnClickListener(v -> {
            if (viewModel.orderBy().getValue() == GetCoinsParams.OrderBy.PRICE) {
                viewModel.setOppositeOrderDirection();
            } else {
                viewModel.setOrderBy(GetCoinsParams.OrderBy.PRICE);
            }
        });
        binding.twentyFourHVolume.setOnClickListener(v -> {
            if (viewModel.orderBy().getValue() == GetCoinsParams.OrderBy.TWENTY_FOUR_H_VOLUME) {
                viewModel.setOppositeOrderDirection();
            } else {
                viewModel.setOrderBy(GetCoinsParams.OrderBy.TWENTY_FOUR_H_VOLUME);
            }
        });
    }

    private Observer<PagedList<Coin>> observeCoins() {
        return list -> {
            controller.submitList(list);
        };
    }

    private Observer<String> observeError() {
        return message -> {
            AlertDialog ad = new AlertDialog.Builder(getContext())
                    .setTitle(R.string.error)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            ad.show();
        };
    }

}
