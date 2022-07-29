package com.example.polygontestapp.di;

import static com.example.polygontestapp.myUtils.Constants.CONTRACT_ADDRESS;
import static com.example.polygontestapp.myUtils.Constants.NODE_ID;

import android.content.Context;

import com.example.polygontestapp.FundMe;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MyContractModule {

    @Singleton
    @Provides
    public FundMe returnFundMe(Web3j web3j, @ApplicationContext Context context){
        return loadContract(CONTRACT_ADDRESS, web3j, getTransactionManager(web3j,context));

    }

    private TransactionManager getTransactionManager(Web3j web3j, Context context) {
        return new RawTransactionManager(web3j, DemoAppSharedPref.getWalletCredentials(context), NODE_ID);
    }

    private FundMe loadContract(String contractAdress, Web3j web3j, TransactionManager transactionManager) {
        return FundMe.load(contractAdress, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
    }

}
