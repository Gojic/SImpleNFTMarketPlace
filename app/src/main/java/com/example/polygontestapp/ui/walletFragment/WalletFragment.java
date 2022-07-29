package com.example.polygontestapp.ui.walletFragment;

import static android.content.ContentValues.TAG;

import static com.example.polygontestapp.ui.walletFragment.JsonReader.readJsonFromUrl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polygontestapp.FundMe;
import com.example.polygontestapp.NFTMarketPlace;
import com.example.polygontestapp.PKCoin;
import com.example.polygontestapp.R;
import com.example.polygontestapp.adapters.NFTFromWalletAdapter;
import com.example.polygontestapp.databinding.FragmentWalletBinding;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.json.JSONObject;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WalletFragment extends Fragment {
    @Inject
    public Web3j web3j;
    private WalletFragmentViewModel walletFragmentViewModel;
    private FragmentWalletBinding binding;
    @Inject
    public FundMe myContract;
    @Inject
    public PKCoin pkCoin;
    @Inject
    public NFTMarketPlace marketPlace;
    private Context context;
    private NavController navController;
    private List<JSONObject> objectList;
    private JSONObject json;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        walletFragmentViewModel =
                new ViewModelProvider(this).get(WalletFragmentViewModel.class);
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false);
        navController = NavHostFragment.findNavController(this);
        objectList = new ArrayList<>();
        try {

            if (pkCoin != null) {
                List list = pkCoin.getItems().sendAsync().get();
                int index = 0;
                for (Object item : list) {

                    if (pkCoin.ownerOf(BigInteger.valueOf(index)).sendAsync().get().toUpperCase(Locale.ROOT).equals(DemoAppSharedPref.loadAdress(context).toUpperCase(Locale.ROOT))) {
                        int finalIndex = index;
                        Thread thread = new Thread(() -> {

                            try {
                                json = readJsonFromUrl(item.toString());
                                objectList.add(json);
                                Log.i(TAG, "ispis statusa: " + marketPlace.getListing(BigInteger.valueOf(finalIndex)).sendAsync().get().status);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        thread.start();

                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // objectList.add(item);
                    }

                 index++;
               }

            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        binding.coinBalanceTV.setText("Matic " + formatBigDecimal(retrieveBalance()));
        try {
            BigInteger bigInteger = myContract.getConversionRate(retrieveBalance()).sendAsync().get();
            binding.priceTV.setText("$ " + formatBigDecimal(bigInteger));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

      setNFTList(objectList, pkCoin);
        binding.publicKeyValueTV.setText(DemoAppSharedPref.loadAdress(context));
        DemoAppSharedPref.setScreenId(context, 2);
        return binding.getRoot();
    }

    //formatiram balans coina sa blockchaina
    private BigDecimal formatWei(BigInteger wei) {
        return Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
    }

    private String formatBigDecimal(BigInteger wei) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(formatWei(wei));
    }

    public BigInteger retrieveBalance() {
        //get wallet's balance
        EthGetBalance balanceWei = null;
        try {
            balanceWei = web3j.ethGetBalance(DemoAppSharedPref.loadAdress(getContext()), DefaultBlockParameterName.LATEST).sendAsync()
                    .get();
            Log.i(TAG, "ispis balansa: " + balanceWei.getBalance());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return balanceWei.getBalance();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setNFTList(List<JSONObject> list, PKCoin pkCoin) {
        RecyclerView recyclerView = binding.rvNftList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        NFTFromWalletAdapter adapter = new NFTFromWalletAdapter(list, context, pkCoin, marketPlace, (obj, json, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("JSONObject", json.toString());
            bundle.putInt("position", position);
            Log.i(TAG, "ispis pozicije iz walleta: " + position);
            navController.navigate(R.id.action_nav_gallery_to_listNFT, bundle);
        }, position -> {

        });

        recyclerView.setAdapter(adapter);
    }

}