package com.example.polygontestapp.ui.walletFragment;

import static android.content.ContentValues.TAG;
import static com.example.polygontestapp.myUtils.Constants.CONTRACT_ADDRESS;
import static com.example.polygontestapp.myUtils.Constants.NODE_ID;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import android.content.Context;
import android.util.Log;

import com.example.polygontestapp.FundMe;
import com.example.polygontestapp.myUtils.DemoAppSharedPref;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.security.Provider;
import java.security.Security;
import java.util.Objects;

public class WalletCreation {
    public Web3j web3j;
    private final String appPath;
    private final String password;
    private String walletName;
    private final Context context;
    private String privateKey;

    boolean isExist;
  //  public IPFSSharing ipfsSharing;
    private String accountAddress;
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public WalletCreation(Context context, Web3j web3j) {
        this.context = context;
        this.web3j = web3j;

        //ipfsSharing = new IPFSSharing("QmQPeNsJPyVWPFDVHb77w8G42Fvo15z4bG2X8D2GhfbSXc");
        setupBouncyCastle();
        appPath = Objects.requireNonNull(context).getApplicationContext().getFilesDir().getAbsolutePath();
        Log.i(TAG, "ispis putanje: " + appPath);
        password = "123123";
        File file = new File(context.getFilesDir() + appPath);// the etherium wallet location
        //create the directory if it does not exist

        if (!file.mkdirs()) {
            file.mkdirs();
        } else {
            Log.i(TAG, "vec postoji: " + file.getAbsolutePath());
        }
        Log.i(TAG, "ispis istinitosti: " + DemoAppSharedPref.getWalletExistanceStatus(context));
        if (DemoAppSharedPref.getWalletExistanceStatus(context)) {
            loadWallet();

        } else {
            isExist = true;
            DemoAppSharedPref.setWalletExistanceStatus(context, isExist);
            createWallet();
        }


    }

//    private static JSONObject process(String seed){
//
//        JSONObject processJson = new JSONObject();
//
//        try {
//            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
//
//            String sPrivatekeyInHex = privateKeyInDec.toString(16);
//
//            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
//            String sAddress = aWallet.getAddress();
//
//
//            processJson.put("address", "0x" + sAddress);
//            processJson.put("privatekey", sPrivatekeyInHex);
//
//
//        } catch (CipherException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        return processJson;
//    }
//
//    private JSONObject metaDataCreation(String dogBreed,long dogPrice,String imgURL,int dogId,Context context){
//
//        JSONObject processJson = new JSONObject();
////        String jsonString = "{
////                \"dogBreed\":"",
////                "dogId":0,
////                "imgURL":"",
////                "dogPrice":0
////}";
//        try {
//
//            processJson.put("dogId",dogId);
//            processJson.put("dogBreed",dogBreed);
//            processJson.put("dogPrice",dogPrice);
//            processJson.put("imgURL",imgURL);
//            File rootFolder = context.getExternalFilesDir(context.getAssets().toString());
//            File jsonFile = new File(rootFolder, "metadata_mumbai_test_net"+"_"+dogBreed+"_"+dogId+".json");
//
//
//            FileWriter writer = new FileWriter(jsonFile);
//            writer.write(processJson.toString());
//          //  Log.i(TAG, "ispis imena metadata json fajla: " + jsonFile);
//            writer.close();
//            //ipfsSharing.addFilesToIPFS(jsonFile,processJson.toString());
//
////            String jsonFileContent = readFile(jsonFile.toString());
////            JSONArray jsonArray = new JSONArray(jsonFileContent);
////
////            for (int i=0;i<jsonArray.length();i++)
////            {
////                JSONObject jsonObj = jsonArray.getJSONObject(i);
////
////                Log.i(TAG, "ispis json fajla: " +  jsonObj.getInt("dogId") +
////                jsonObj.getString("dogBreed") +
////                jsonObj.getString("dogPrice") +
////                jsonObj.getString("imgURL"));
////            }
//
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        return processJson;
//    }

    public void sendTranscation(){
        Transfer transfer = new Transfer(web3j, getTransactionManager());
        try {
            TransactionReceipt transactionReceipt = transfer.sendFunds(
                    "0x438978a0cc03fbded858936a05a1a2a5e25e4f3e",
                    BigDecimal.valueOf(0.5),
                    Convert.Unit.ETHER,
                    GAS_PRICE,
                    GAS_LIMIT
            ).sendAsync().get();
            Log.i(TAG, "Transaction hash: " + transactionReceipt.getTransactionHash());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createWallet() {
        try {
            walletName = WalletUtils.generateLightNewWalletFile(password, new File(appPath));
            Credentials credentials = WalletUtils.loadCredentials(password, appPath + "/" + walletName);
            DemoAppSharedPref.setWalletCredentials(context, credentials);
            // Get the account address
            accountAddress = credentials.getAddress();
            DemoAppSharedPref.saveAdress(context,accountAddress);
            Log.i(TAG, "createWallet: " + credentials.getAddress());
            Log.i(TAG, "ispis accountAdress: " + accountAddress);
            loadContract(CONTRACT_ADDRESS, web3j, getTransactionManager());

         //   walletInterface.getContractFromWallet(loadContract(CONTRACT_ADDRESS, web3j, getTransactionManager()));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "Wallet Creating failed: " + e.getMessage());
        }



    }


    private void loadWallet() {
// Load the JSON encryted wallet
        loadContract(CONTRACT_ADDRESS, web3j, getTransactionManager());
        //  credentials = WalletUtils.loadCredentials(password, appPath + "/" + walletName);
        Credentials credentials = DemoAppSharedPref.getWalletCredentials(context);
        // Get the unencrypted private key into hexadecimal
       // privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        setPrivateKey(privateKey);
        Log.i(TAG, "ispis accountAdress: " + credentials.getAddress());
      //  walletInterface.getContractFromWallet(loadContract(CONTRACT_ADDRESS, web3j, getTransactionManager()));
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public TransactionManager getTransactionManager() {
        return new RawTransactionManager(web3j, DemoAppSharedPref.getWalletCredentials(context), NODE_ID);
    }

    public FundMe loadContract(String contractAdress, Web3j web3j, TransactionManager transactionManager) {
        return FundMe.load(contractAdress, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
    }

}
