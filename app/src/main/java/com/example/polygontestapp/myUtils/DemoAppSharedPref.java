package com.example.polygontestapp.myUtils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.web3j.crypto.Credentials;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DemoAppSharedPref {
    public static final String WALLET_EXIST = "wallet_exist";
    public static final String CREDENTIALS = "credentials";
    public static final String MY_PUBLIC_KEY = "public_key";
    public static final String SCREEN_ID = "screenId";
    public static final String NFT_LIST = "nft";
    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void saveList(List<String> list, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savenftList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(NFT_LIST, json);
        editor.apply();
    }

    public static List<String> loadList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savenftList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(NFT_LIST, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        List<String> list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
    public static void setWalletExistanceStatus(Context context, boolean walletExist) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(WALLET_EXIST, walletExist).apply();
    }

    public static boolean getWalletExistanceStatus(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(WALLET_EXIST, false);
    }

    public static void saveAdress(Context context, String address) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(MY_PUBLIC_KEY, address).apply();
    }

    public static String loadAdress(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(MY_PUBLIC_KEY, null);
    }

    public static void setWalletCredentials(Context context, Credentials credentials) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WalletCredentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(credentials);
        editor.putString(CREDENTIALS, json);
        editor.apply();
    }

    public static Credentials getWalletCredentials(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WalletCredentials", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CREDENTIALS, null);
        Type type = new TypeToken<Credentials>() {
        }.getType();

        return gson.fromJson(json, type);
    }
    public static void setScreenId(Context context, int id) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(SCREEN_ID, id).apply();
    }

    public static int getScreenId(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(SCREEN_ID, 0);
    }

}

