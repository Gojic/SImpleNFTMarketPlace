package com.example.polygontestapp.ui.mintNFT;

import static android.content.ContentValues.TAG;

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

import com.example.polygontestapp.PKCoin;
import com.example.polygontestapp.databinding.FragmentMinitingNftBinding;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

//metaFajlovi za test mintovanje NFTjeva
//https://ipfs.io/ipfs/bafkreiheyjisbbzgavag2ghfph3ru2egvwr36eqgde3vmrjjlekicinyy4?filename=retriver.json
//https://ipfs.io/ipfs/bafkreibse5olb6ho2pdzijcxz2qkvqndswq4nwd7wzisc24hpoue5pgr5y?filename=haski.json
//https://ipfs.io/ipfs/bafkreihjvcl4ebi6gyhfxwwh37mihdeyt2w3oejwg5pxq3wxzx2fjmmdaa?filename=rot.json
//https://ipfs.io/ipfs/bafkreid2naqfxrh3las2a6vheidvzau2vwytxrf3nxpqnmiysyb2fhbqbu?filename=pug.json

@AndroidEntryPoint
public class MintNFTFragment extends Fragment {
    @Inject
    public Web3j web3j;
    private FragmentMinitingNftBinding binding;
    private Context context;
    @Inject
    public PKCoin pkCoinContract;
    private ArrayList<String> listOfExistentNFTs;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMinitingNftBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listOfExistentNFTs = new ArrayList<>();
        context = getContext();
        navController = NavHostFragment.findNavController(this);
        Log.i(TAG, "ispis liste nftjeva: " + DemoAppSharedPref.loadList(context));
        binding.mintBTN.setOnClickListener(view -> {
            listOfExistentNFTs.add(binding.metafileET.getText().toString());

            String userInput = binding.metafileET.getText().toString();
            if (!DemoAppSharedPref.loadList(context).contains(userInput)) {
                DemoAppSharedPref.saveList(listOfExistentNFTs, context);
                if (!retrieveBalance().equals(BigInteger.valueOf(0))) {
                    try {
                        Toast.makeText(context, "Minting pending. Please wait,this can take some time!", Toast.LENGTH_LONG).show();
                        pkCoinContract.createCollectible(binding.metafileET.getText().toString().trim()).sendAsync().get();
                        navController.popBackStack();
                        Toast.makeText(context, "Minted Successful!", Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something went wrong! Please check your ipfs URI!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please provide sufficient funds, including gas!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "You Can't Mint Same NFT Multiply Times!", Toast.LENGTH_SHORT).show();
            }

        });

        return root;
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

}