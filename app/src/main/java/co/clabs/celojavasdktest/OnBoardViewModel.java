package co.clabs.celojavasdktest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

public class OnBoardViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    Context mContext;
    SharedPreferences mWalletDetails = mContext.getSharedPreferences(
            "walletDetails",
            Context.MODE_PRIVATE
    );
    Boolean isUserDataLoaded = mWalletDetails.
            getBoolean("established",false);

    {
        Log.d("Is user loaded:", isUserDataLoaded.toString());
    }

}