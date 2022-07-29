package com.example.polygontestapp.ui.walletFragment.sellBuyNFT;

import static android.content.ContentValues.TAG;
import static com.example.polygontestapp.myUtils.Constants.CONTRACT_ADDRESS_NFT_MARKETPLACE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.polygontestapp.FundMe;
import com.example.polygontestapp.NFTMarketPlace;
import com.example.polygontestapp.PKCoin;
import com.example.polygontestapp.R;
import com.example.polygontestapp.databinding.FragmentListNFTBinding;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SellBuyNFT extends Fragment {

    private FragmentListNFTBinding binding;
    private Context context;
    private NavController navController;
    private JSONObject json;
    @Inject
    public NFTMarketPlace marketPlace;
    @Inject
    public PKCoin pkCoin;
    @Inject
    public FundMe myContract;
    private ArrayList<Integer> list;
    private int position;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        context = getContext();
        list = new ArrayList<>();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_n_f_t, container, false);
        navController = NavHostFragment.findNavController(this);

        handleDifferentScreens();
        if (getArguments() != null) {
            try {
                json = new JSONObject(getArguments().getString("JSONObject"));
                position = getArguments().getInt("position");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "ispis prenete pozicije: " + position);
        try {
            binding.nameTV.setText(json.get("name").toString());
            Glide.with(context)
                    .load(json.get("image"))
                    .into(binding.nftImageIMG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding.sellBTN.setOnClickListener(view -> {

            if (DemoAppSharedPref.getScreenId(context) == 1) {
                String priceInput = binding.priceET.getText().toString();
                if (isEmpty(binding.priceET) || binding.priceET.getText().toString().equals("0")) {
                    try {
                        Toast.makeText(context, "Purchase is pending. Please wait,this can take some time!", Toast.LENGTH_LONG).show();

                        BigDecimal convertedPrice = Convert.toWei(priceInput, Convert.Unit.ETHER);
                        BigInteger bigInteger = convertedPrice.toBigInteger();
                        marketPlace.buyToken(BigInteger.valueOf(position), bigInteger).sendAsync().get();
                        Toast.makeText(context, "The Purchase is Successful! NFT is in your Wallet!", Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Price field can't be empty or 0", Toast.LENGTH_SHORT).show();
                }

            } else {

                try {


                    if (!isEmpty(binding.priceET) || binding.priceET.getText().toString().equals("0")) {
                        Toast.makeText(context, "Minimum Price Must be 1 MATIC", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Listing is pending. Please wait,this can take some time!", Toast.LENGTH_LONG).show();
                        String priceInput = binding.priceET.getText().toString();
                        BigDecimal convertedPrice = Convert.toWei(priceInput, Convert.Unit.ETHER);
                        BigInteger bigInteger = convertedPrice.toBigInteger();
                        pkCoin.setApprovalForAll(CONTRACT_ADDRESS_NFT_MARKETPLACE, true).sendAsync().get();
                        marketPlace.listToken(pkCoin.getContractAddress(), getTokenId(), bigInteger).sendAsync().get();
                        Toast.makeText(context, "Successful listed! NFT is in Marketplace!", Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }

        });
        return binding.getRoot();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() > 0;
    }

    private BigInteger getTokenId() throws ExecutionException, InterruptedException {
        for (int i = 0; i < pkCoin.getItems().sendAsync().get().size(); i++) {

            if (pkCoin.ownerOf(BigInteger.valueOf(i)).sendAsync().get().toUpperCase(Locale.ROOT).equals(DemoAppSharedPref.loadAdress(context).toUpperCase(Locale.ROOT))) {
                list.add(i);
            }
        }
        Log.i(TAG, "ispis filtrirane liste: " + list);
        return BigInteger.valueOf(list.get(position));
    }

    @SuppressLint("SetTextI18n")
    private void handleDifferentScreens() {
        if (DemoAppSharedPref.getScreenId(context) == 1) {
            binding.sellBTN.setText("Buy This NFT");
            getActivity().setTitle("Purchase of NFT");
        } else {
            binding.sellBTN.setText("List This NFT");
            getActivity().setTitle("Selling NFT");
        }

    }
}