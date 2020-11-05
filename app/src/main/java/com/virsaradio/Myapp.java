package com.virsaradio;

import android.app.Application;

/**
 * Created by khushdeep-android on 17/4/18.
 */

public class Myapp extends Application {

    private static Myapp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized Myapp getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
