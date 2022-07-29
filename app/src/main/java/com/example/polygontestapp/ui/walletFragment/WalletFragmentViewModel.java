package com.example.polygontestapp.ui.walletFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WalletFragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WalletFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}