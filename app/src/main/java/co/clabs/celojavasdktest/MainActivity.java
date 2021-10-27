package co.clabs.celojavasdktest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.celo.contractkit.AccountBalance;
import org.celo.contractkit.CeloContract;
import org.celo.contractkit.ContractKit;
import org.celo.contractkit.ContractKitOptions;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import co.clabs.celojavasdktest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}

class CeloKit extends AsyncTask<String, Void, String> {
    private static final String TAG = "CeloKit AsyncTask";
    private Context mContext;
    private String private_key = "PRIVATE_KEY";
    private String password = "SomeSp3cialPa55word_!*!";
    private Credentials credentials;

    JSONObject obj = new JSONObject();
    AccountBalance balance;
    String address;
    ProgressDialog progDailog;
    Bip39Wallet generatedWallet, restoredWallet;
    File desitnationDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    SharedPreferences mWalletDetails;

//  Examples: Creating/restoring wallet
//    {
//    try
//        {
//
//            // New Bip39 wallet, password protected and stored within device download directory
//            generatedWallet = WalletUtils.
//                    generateBip39Wallet(
//                            password,
//                            desitnationDirectory
//                    );
//            // Restore wallet from mnemonic wallet words. Utilizing previously created wallet for restoration example.
//            restoredWallet = WalletUtils.
//                    generateBip39WalletFromMnemonic(
//                            password,
//                            generatedWallet.getMnemonic(),
//                            desitnationDirectory
//                    );
////            mWalletDetails.edit().putString("mnemonic",generatedWallet.getMnemonic()).apply();
//            Log.d("New wallet", generatedWallet.getMnemonic());
//            Log.d("Restored wallet", restoredWallet.getMnemonic());
//        } catch(
//        CipherException e)
//        {
//            Log.e("-=> Cypher Exception: ", e.toString());
//        } catch(
//        IOException e)
//        {
//            Log.e("-=> IO Exception: ", e.toString());
//        }
//}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(mContext);
        progDailog.setMessage("Communicating with Celo...");
        progDailog.setCancelable(false);
        progDailog.show();
        mWalletDetails = mContext.getSharedPreferences("walletDetails", mContext.MODE_PRIVATE);
    }


    private Exception exception;
    ContractKit contractKit;

    public CeloKit(Context context){
        this.mContext = context;
    }

    //  Define the interface used to provide results
    public interface Callback {
        public void onDataLoaded(JSONObject obj);
    }
    private Callback  mCb;

    public CeloKit(Context context, Callback cb) {
        this.mContext = context;
        mCb = cb;
    }

    protected String doInBackground(String... urls) {
        try {
            Web3j web3j = Web3j.build(new HttpService(urls[0]));
            ContractKitOptions config = new ContractKitOptions.Builder()
                    .setFeeCurrency(CeloContract.GoldToken)
                    .setGasPrice(BigInteger.valueOf(21_000))
                    .build();
            Log.d("Web3:","Web3 Object created");
            contractKit = new ContractKit(web3j,config);
            Log.d("Contract Kit:","Contract kit object created");

            // Retrieve persisted wallet state from shared preferences
            Boolean isUserDataLoaded = mWalletDetails.getBoolean("established",false);
            Log.d("Load Wallet Data:",isUserDataLoaded.toString());

            if(isUserDataLoaded) {
                // Restore existing credentials
                String mnemonic = mWalletDetails.getString("mnemonic",null);
                Log.d("Mnemonic:",mnemonic);
                credentials = WalletUtils.loadBip39Credentials(password, mnemonic);
                contractKit.addAccount(credentials);
                address = contractKit.getAddress();
                balance = contractKit.getTotalBalance(address);
                return contractKit.getAddress();
            } else {
                // generate new wallet
                generatedWallet = WalletUtils.
                        generateBip39Wallet(
                                password,
                                desitnationDirectory
                        );
                Log.d("New Wallet:",generatedWallet.toString());

                // persist wallet data using shared preferences
                mWalletDetails.edit().putString("mnemonic", generatedWallet.getMnemonic()).apply();
                mWalletDetails.edit().putBoolean("established", true).apply();

                Log.d("Persist wallet data:","completed");

                // Protect sensitive data!
                String messageString = "some cool message";

                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[8];
                random.nextBytes(salt);

                // PBKDF2 with HMAC-SHA1 AES key from password string
                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
                SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] keyBytes = f.generateSecret(spec).getEncoded();

                // PublicKey from password string
                SecretKey secret = new SecretKeySpec(keyBytes, "AES");

                // Encryption
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secret);
                byte[] cipherText = cipher.doFinal(messageString.getBytes("UTF-8"));

                // Decryption
                cipher.init(Cipher.DECRYPT_MODE, secret);
                String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");

                Log.d("messageString",messageString);
                Log.d("encryptedMessage",cipherText.toString());
                Log.d("decryptString",decryptString);

                // todo: temp solution
                String mnemonic = mWalletDetails.getString("mnemonic",null);
                credentials = WalletUtils.loadBip39Credentials(password, mnemonic);
                contractKit.addAccount(credentials);
                address = contractKit.getAddress();
                balance = contractKit.getTotalBalance(address);
                return contractKit.getAddress();
            }
        } catch (Exception e) {
            this.exception = e;
            Log.e("-=> Exception: ", e.toString());
        } finally {
            Log.d("-=> AsyncTask",  "Try/catch -> final execution completed");
        }
        return "DoInBackground -> Incomplete process";
    }

    protected void onPostExecute(String data){
        Log.d(TAG, "onPostExecute data: "+data);
        try {
            BigDecimal cUSDSum = new BigDecimal(String.valueOf(balance.cUSD));
            BigDecimal celoSum = new BigDecimal(String.valueOf(balance.CELO));

            obj.put("address", address);
            obj.put("cusd", Convert.fromWei(cUSDSum, Convert.Unit.ETHER));
            obj.put("celo", Convert.fromWei(celoSum, Convert.Unit.ETHER));
            mCb.onDataLoaded(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        };
        progDailog.cancel();    }
}
