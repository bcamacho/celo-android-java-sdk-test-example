package co.clabs.celojavasdktest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.celo.contractkit.ContractKit;
import org.json.JSONObject;

import co.clabs.celojavasdktest.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment implements CeloKit.Callback {

    TextView mAddress,mCelo,mCusd;
    CeloKit  mAsyncRetriever;
    Button mBtn1;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  Get the TextView now where we want to show results.
        //  This avoids calling findViewById() constantly.
        mAddress = (TextView)view.findViewById(R.id.tv_address);
        mCelo = (TextView)view.findViewById(R.id.tv_celo);
        mCusd = (TextView)view.findViewById(R.id.tv_cusd);


        binding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack(binding.getRoot()., FragmentManager.POP_BACK_STACK_INCLUSIVE);

                // Request updated data by detaching/attaching second fragment
                NavHostFragment.findNavController(FirstFragment.this)
                      .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        mBtn1 = (Button)view.findViewById(R.id.btnDeepLink);
        mBtn1.setText("Valora DeepLink");
        mBtn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
/*
                --> Valora Deep Links <--

                Valora source code provides hints into Deeplinks:
                https://github.com/valora-inc/wallet/blob/main/packages/mobile/src/app/saga.test.ts#L48-L75
                https://github.com/valora-inc/wallet/blob/main/packages/mobile/src/app/saga.test.ts#L48-L75
                https://github.com/valora-inc/wallet/blob/fa65a9a325c5529fa4aac67e38ac046c82ca0dc2/packages/mobile/src/send/utils.ts#L252

                Testing Deeplinks with ADB
                ./adb shell am start -W -a android.intent.action.VIEW -d "celo://wallet/cashIn" co.clabs.valora
                ./adb shell am start -W -a android.intent.action.VIEW -d "celo://wallet/bidali"
                ./adb shell am start -W -a android.intent.action.VIEW -d "celo://wallet/pay?address=0x0000000000000000000000000000000000000000&displayName=Payment+Test&currencyCode=cUSD&amount=1&comment=From+Android%2C+with+Love"

                Utilizing deeplinks within onclick intent
*/
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("celo://wallet/bidali"));
                    startActivity(intent);
                } catch(Exception error){
                    Log.d("URI error", String.valueOf(error));
                    Toast.makeText(getContext(), "Valora not found, please install",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        // Keep a reference to the AsyncTask so we can properly
        // cancel it when our lifecycle events dictate so.
        super.onResume();
        // Instantiate CeloKit as a method for use within fragment lifecycle
        mAsyncRetriever = new CeloKit(getContext(),this);
        // Configure network connection
        mAsyncRetriever.execute(ContractKit.ALFAJORES_TESTNET);
    }

    @Override
    public void onPause() {
        //  If we have a pending data load going on, kill it.
        super.onPause();
        if (mAsyncRetriever != null) {
            mAsyncRetriever.cancel(true);
            mAsyncRetriever = null;
        }
    }

    @Override
    public void onDataLoaded(JSONObject obj) {
        //  Only pulling the first result provided
        try {
            mAddress.setText("Address:\n"+obj.getString("address"));
            mCelo.setText("Celo Tokens:\n"+obj.getString("celo"));
            mCusd.setText("Celo USD:\n"+obj.getString("cusd"));
        } catch (Exception error){
            Log.e("Error",error.toString());
        }

        //  The RetrieveData is done, get rid of our reference
        mAsyncRetriever = null;
    }
}