package com.example.polygontestapp.ui.transaction;

import static android.content.ContentValues.TAG;
import static com.example.polygontestapp.myUtils.Constants.NODE_ID;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.polygontestapp.R;
import com.example.polygontestapp.databinding.FragmentTransactionBinding;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TransactionFragment extends Fragment {
    @Inject
    public Web3j web3j;
    private Context context;
    private FragmentTransactionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);
        binding.sendBTN.setOnClickListener(view -> {
            if (!isValidEnsName(binding.addressET.getText().toString()))
                onClickSend(Integer.parseInt(binding.amountET.getText().toString()), binding.addressET.getText().toString());
            else
                Toast.makeText(context, "Invalid Address", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    public static boolean isValidEnsName(String input) {
        return input != null  // will be set to null on new Contract creation
                && (input.contains(".") || !WalletUtils.isValidAddress(input));
    }


    public void onClickSend(int sendingValue, String sendingTo) {
        AlertDialog.Builder dialogBuider = new AlertDialog.Builder(context);
        dialogBuider.setMessage("Are you sure you want to send funds?");
        dialogBuider.setPositiveButton("Yes,send it", (dialog, which) -> {
            sendTranscation(sendingValue,sendingTo);
        });

        dialogBuider.setNegativeButton("No,cancel transaction", (dialog, id) -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = dialogBuider.create();
        alertDialog.show();
    }

    public void sendTranscation(int sendingValue, String sendingTo) {

        Transfer transfer = new Transfer(web3j, getTransactionManager());
        try {
            transfer.sendFunds(
                    sendingTo,
                    BigDecimal.valueOf(sendingValue),
                    Convert.Unit.ETHER,
                    GAS_PRICE,
                    GAS_LIMIT
            ).sendAsync().get();
            Toast.makeText(context, "Request Successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i(TAG, "ipis greske: " + e.toString());
            e.printStackTrace();

        }

    }

    public TransactionManager getTransactionManager() {
        return new RawTransactionManager(web3j, DemoAppSharedPref.getWalletCredentials(context), NODE_ID);
    }
}