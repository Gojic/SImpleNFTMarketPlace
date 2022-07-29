package com.example.polygontestapp.adapters;

import static com.example.polygontestapp.myUtils.Constants.CONTRACT_ADDRESS_PKCOIN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polygontestapp.NFTMarketPlace;
import com.example.polygontestapp.PKCoin;
import com.example.polygontestapp.databinding.NftItemBinding;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NFTFromWalletAdapter extends RecyclerView.Adapter<NFTFromWalletAdapter.Holder> {
    private final List<JSONObject> list;
    private final Context context;
    private final PKCoin pkCoin;
    private final OnClickInterfce onClickInterfce;
    private final OnClickCancel onClickCancel;
    private NFTMarketPlace nftMarketPlace;
    private NFTMarketPlace.Listing listing;
    private List<Integer> integerArrayList;
    private List<Integer> integerArrayListIndex;
    public NFTFromWalletAdapter(List<JSONObject> list, Context context, PKCoin pkCoin, NFTMarketPlace nftMarketPlace, OnClickInterfce onClickInterfce, OnClickCancel onClickCancel) {
        this.list = list;
        this.pkCoin = pkCoin;
        this.context = context;
        this.nftMarketPlace = nftMarketPlace;
        this.onClickInterfce = onClickInterfce;
        this.onClickCancel = onClickCancel;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NftItemBinding binding = NftItemBinding.inflate(layoutInflater, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        JSONObject model = list.get(position);
        holder.bind(model);
//        try {
//            listing = nftMarketPlace.getListing(BigInteger.valueOf(position)).sendAsync().get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }


        holder.binding.sellBTN.setOnClickListener(view -> onClickInterfce.onClickListener(list.get(position), model, holder.getAdapterPosition()));
        holder.binding.cancelBTN.setOnClickListener(view -> onClickCancel.onClickCancelListener(holder.getAdapterPosition()));
        try {
            handleDifferentScreens(holder.binding.sellBTN, holder.binding.cancelBTN, holder.binding.priceTV, position);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void getPositionList(List<Integer> integerArrayList) {
        this.integerArrayListIndex = integerArrayList;
    }


    public interface OnClickInterfce {
        void onClickListener(Object obj, JSONObject json, int position);
    }

    public interface OnClickCancel {
        void onClickCancelListener(int position);
    }

    class Holder extends RecyclerView.ViewHolder {
        private final NftItemBinding binding;

        public Holder(NftItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
        public void bind(final JSONObject modelClass) {

            try {
                Glide.with(context)
                        .load(modelClass.get("image"))
                        .into(binding.nftImageIMG);
//                ArrayList<Integer> integers = new ArrayList<>();
                binding.idTV.setText("Dog id: " + modelClass.getInt("id"));


//                for (int i = 0; i < pkCoin.getItems().sendAsync().get().size(); i++) {
//
////                    if (pkCoin.ownerOf(BigInteger.valueOf(i)).sendAsync().get().equals(DemoAppSharedPref.loadAdress(context))) {
////                        integers.add(i);
////                        //   getIPFSThread(item);
////                    }
//
//                    //  binding.tokenIdTV.setText("TokenId: " + i);
//
//                }
                //  for (int i = 0; i < integers.size(); i++)
                binding.tokenIdTV.setText("TokenId: " + 0);
                binding.contractAddressTV.setText("Contract Address: " + CONTRACT_ADDRESS_PKCOIN);
                binding.tokenStandardTV.setText("Token Standard: " + "ERC721");
                binding.blockChainTV.setText("BlockChain: " + "Polygon/Mumbai");
                binding.nameTV.setText("Item Name: " + modelClass.get("name"));

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getIntegerArrayList(List<Integer> integerArrayList){
        this.integerArrayList = integerArrayList;
    }
    @SuppressLint("SetTextI18n")
    private void handleDifferentScreens(Button sell, Button cancel, TextView priceInput, int position) throws ExecutionException, InterruptedException {
        if (DemoAppSharedPref.getScreenId(context) == 1) {

            if (!nftMarketPlace.getListing(BigInteger.valueOf(integerArrayListIndex.get(position))).sendAsync().get().seller.equals(DemoAppSharedPref.loadAdress(context))) {
                sell.setText("Buy It");
                sell.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);

            } else {
                cancel.setText("Cancel Listing");
                cancel.setVisibility(View.VISIBLE);
                sell.setVisibility(View.GONE);

            }

           // priceInput.setText("Price in MATIC: " + nftMarketPlace.getTokenPrice(BigInteger.valueOf(integerArrayList.get(position))).sendAsync().get());
            priceInput.setText("Price in MATIC: " + Convert.fromWei(String.valueOf(nftMarketPlace.getTokenPrice(BigInteger.valueOf(integerArrayList.get(position))).sendAsync().get()), Convert.Unit.ETHER));

        } else {
            cancel.setVisibility(View.GONE);
            sell.setText("Sell It");
            priceInput.setText("Price in MATIC: Not Listed!");
        }
    }
}
