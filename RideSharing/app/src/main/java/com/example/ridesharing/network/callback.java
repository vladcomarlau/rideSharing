package com.example.ridesharing.network;

public interface callback<R>{
    void runResultOnUiThread(R result);
}