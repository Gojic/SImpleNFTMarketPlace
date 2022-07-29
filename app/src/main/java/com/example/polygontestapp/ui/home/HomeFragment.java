package com.example.polygontestapp.ui.home;

import static android.content.ContentValues.TAG;
import static com.example.polygontestapp.ui.walletFragment.JsonReader.readJsonFromUrl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polygontestapp.NFTMarketPlace;
import com.example.polygontestapp.PKCoin;
import com.example.polygontestapp.R;
import com.example.polygontestapp.adapters.NFTFromWalletAdapter;
import com.example.polygontestapp.databinding.FragmentHomeBinding;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    @Inject
    public Web3j web3j;
    private FragmentHomeBinding binding;
    @Inject
    public PKCoin pkCoin;
    @Inject
    public NFTMarketPlace marketPlace;
    private Context context;
    private NavController navController;
    private List<JSONObject> objectList;
    private JSONObject json;
    private ArrayList<Integer> integerArrayList;
    private List<JSONObject> filteredJSONObjectArrayList;
    private ArrayList<BigInteger> nftMarketPlaceArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        DemoAppSharedPref.setScreenId(context, 1);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        navController = NavHostFragment.findNavController(this);
        objectList = new ArrayList<>();

        try {

            if (pkCoin != null) {

                for (Object item : pkCoin.getItems().sendAsync().get()) {
                    Thread thread = new Thread(() -> {

                        try {
                            json = readJsonFromUrl(item.toString());
                            objectList.add(json);
                           for (JSONObject jsonObject: objectList)
                            Log.i(TAG, "ispis liste: " + jsonObject);
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

                }
            }

            integerArrayList = new ArrayList<>();
            filteredJSONObjectArrayList = new ArrayList<>();
            nftMarketPlaceArrayList = new ArrayList<>();
            BigInteger tokenId = marketPlace.getIndex().sendAsync().get();
            int tokenIdInteger = tokenId.intValue();

            for (int i = 0; i < tokenIdInteger; i++) {
                nftMarketPlaceArrayList.add(marketPlace.getStatus(BigInteger.valueOf(i)).sendAsync().get());
            }


            for (int i = 0; i < indexOfAll(BigInteger.valueOf(1), nftMarketPlaceArrayList).size(); i++) {

                BigInteger nftId = marketPlace.getListing(BigInteger.valueOf(indexOfAll(BigInteger.valueOf(1), nftMarketPlaceArrayList).get(i))).sendAsync().get().tokenId;
                int nftIdInteger = nftId.intValue();
                integerArrayList.add(nftIdInteger);
              filteredJSONObjectArrayList.addAll(Collections.singleton(objectList.get(nftIdInteger)));
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
       setNFTList(filteredJSONObjectArrayList, pkCoin, marketPlace, indexOfAll(BigInteger.valueOf(1), nftMarketPlaceArrayList));
        return root;
    }

    static <T> List<Integer> indexOfAll(T obj, List<T> list) {
        final List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (obj.equals(list.get(i))) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setNFTList(List<JSONObject> list, PKCoin pkCoin, NFTMarketPlace nftMarketPlace, List<Integer> integerArrayList) {

        RecyclerView recyclerView = binding.rvNftList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        NFTFromWalletAdapter adapter = new NFTFromWalletAdapter(list, context, pkCoin, nftMarketPlace, (obj, json, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("JSONObject", json.toString());
            bundle.putInt("position", integerArrayList.get(position));
            navController.navigate(R.id.action_nav_home_to_sellBuyNFT, bundle);
        }, position -> {
            try {
                Toast.makeText(context, "Canceling is pending. Please wait,this can take some time!", Toast.LENGTH_LONG).show();
                marketPlace.cancel(BigInteger.valueOf(integerArrayList.get(position))).sendAsync().get();
                Toast.makeText(context, "Successfully canceled, you can find NFT in your wallet!", Toast.LENGTH_SHORT).show();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        });
        adapter.getIntegerArrayList(integerArrayList);
        adapter.getPositionList(integerArrayList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}