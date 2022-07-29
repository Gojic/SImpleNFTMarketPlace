package com.example.polygontestapp.di;

import static com.example.polygontestapp.myUtils.Constants.NODE_URL;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class PolygonModule {

    @Singleton
    @Provides
    public Web3j getmWeb3j() {
        return Web3j.build(new HttpService(NODE_URL));

    }

//    @Singleton
//    @Provides
//    public MyContract getMyContract() {
//        return loadContract(MY_CONTRACT_ADRESS, getmWeb3j(), getTransactionManager());
//    }
//
//    @Singleton
//    @Provides
//    public Credentials getCredentialsFromPrivateKey() {
//        return Credentials.create(PRIVATE_KEY);
//    }
//
//    @Singleton
//    @Provides
//    public TransactionManager getTransactionManager() {
//        return new RawTransactionManager(getmWeb3j(), getCredentialsFromPrivateKey(), NODE_ID);
//    }
//
//    public MyContract loadContract(String contractAdress, Web3j web3j, TransactionManager transactionManager) {
//        return MyContract.load(contractAdress, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//    }
}
