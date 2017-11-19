package tech.disruptfin.wackhestern.disruptfintech;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    @BindView(R.id.spinner1) Spinner spinner;
    @BindView(R.id.textO) TextView mTextView;
    @BindView(R.id.et_title) EditText titleEt;
    @BindView(R.id.et_body) EditText bodyEt;
    @BindView(R.id.ticket) TextView ticketed;
    @BindView(R.id.name) TextView named;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.textView2) TextView curVal;
    @BindView(R.id.textView3) TextView curbNum;
    private TextView mResponseTv;
    private APIService mAPIService;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    SharedPreferences mDefaultPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        named.setText(mDefaultPreferences.getString(getResources().getString(R.string.current_name), "Your Name"));

        list = new ArrayList<String>();
        list.add("AAPL");
        list.add("MSFT");
        list.add("GOOGL");
        list.add("TSLA");

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.getSelectedItemPosition();
        adapter.notifyDataSetChanged();

        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();



        Timer timer = new Timer();
        TimerTask updateBall = new UpdateBallTask();
        timer. schedule(updateBall, 0, 2500);


    }

    @OnItemSelected(R.id.spinner1)
    void onItemSelected(int position) {
        Log.wtf("POSITION",position+"");
        ticketed.setText(list.get(position));
    }

    @OnClick (R.id.btn_submit)
    public void btn_submit(View view){
        String stockName = titleEt.getText().toString().trim();
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(stockName) && !TextUtils.isEmpty(amount)) {
            sendPostSell(stockName, amount);
        }
    }

    @OnClick(R.id.signout)
    public void signout(View view){
        this.startActivity(new Intent(this, LaunchActivity.class));
    }

    @OnClick (R.id.btn_st_funds)
    public void btn_st_funds(View view){
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(amount)) {
        setPost(amount);}

    }

    @OnClick (R.id.btn_buy)
    public void btn_buy(View view){
        String stockName = titleEt.getText().toString().trim();
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(stockName) && !TextUtils.isEmpty(amount)) {
            buyPost(stockName, amount);
        }
    }


    public void curNum(String stockName) {
        Log.wtf("SET","Data");

        mAPIService.curNum(new FooRequestNet(stockName)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){
                    //curbNum.setText("Stocks owned: "+response.body().toString());
                    curbNum.setText("Stocks owned:" + 4);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API set.");
            }
        });
    }

    public void curPrice(String stockName) {
        Log.wtf("SET","Data");

        mAPIService.curPrice(new FooRequestNet(stockName)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){
                    curVal.setText("Each Stock's value: $"+Math.round(Float.parseFloat(response.body().toString())));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API set.");
            }
        });
    }

    public void val() {
        Log.wtf("SET","Data");

        mAPIService.val(new FooRequestNet("NO")).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){
                    balance.setText("Portfolio: $" + Math.round(Float.parseFloat(response.body().toString())));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API set.");
            }
        });
    }

    public void last() {
        Log.wtf("SET","Data");

        mAPIService.last(new FooRequestNet("NO")).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){
                    boolean newone = true;
                    for(String string : list){
                        if(response.body().toString().equals(string)){
                            newone=false;
                        }
                    }
                    if(newone){
                    list.add(response.body().toString());
                    adapter.add(response.body().toString());
                    adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API set.");
            }
        });
    }

    public void buyPost(String stockName, String amount) {
        Log.wtf("SET","Data");
        mAPIService.buyPost(new FooRequest(amount, stockName)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API set." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API set.");
            }
        });
    }

    public void sendPostAvFunds() {
        //Log.wtf("AV","FUNDS");
        mAPIService.avFund(new FooRequest("NOT","VALID")).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API av." + response.body().toString());
                    mTextView.setText("Available Funds: $"+Math.round(Float.parseFloat(response.body().toString())));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API av.");
            }
        });
    }

    public void sendPostSell(String stockName, String amount) {
        Log.wtf("Stockname",""+stockName);
        mAPIService.savePost(new FooRequest(amount, stockName)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void canBuyPost(String stockName, String amount) {
        Log.wtf("Stockname",""+stockName);
        mAPIService.canBuyPost(new FooRequest(amount, stockName)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void setPost(String amount) {
        Log.wtf("SET","FUNDS");
        mAPIService.setPost(new FooRequestSet(amount)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API set." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to AP setI.");
            }
        });
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }

    class UpdateBallTask extends TimerTask {

        public void run() {
            sendPostAvFunds();
            val();
            last();
            curPrice(list.get(spinner.getSelectedItemPosition()));
            curNum(list.get(spinner.getSelectedItemPosition()));
        }
    }
}


