package tech.disruptfin.wackhestern.disruptfintech;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    @BindView(R.id.spinner1) Spinner spinner;
    @BindView(R.id.textO) TextView mTextView;
    @BindView(R.id.et_title) EditText titleEt;
    @BindView(R.id.et_body) EditText bodyEt;
    private TextView mResponseTv;
    private APIService mAPIService;
    private APIData mAPIData;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        list = new ArrayList<String>();
        //for(String string:getResources().getStringArray(R.array.country_arrays)){
        //    list.add(string);
        //}
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();
        mAPIData = ApiUtils.getAPIData();



        Timer timer = new Timer();
        TimerTask updateBall = new UpdateBallTask();
        timer. schedule(updateBall, 0, 2500);
    }

    @OnClick (R.id.btn_submit)
    public void btn_submit(View view){
        String stockName = titleEt.getText().toString().trim();
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(stockName) && !TextUtils.isEmpty(amount)) {
            sendPostSell(stockName, amount);
        }
    }

    @OnClick (R.id.btn_av_funds)
    public void btn_av_funds(View view){
        sendPostAvFunds();
    }

    @OnClick (R.id.btn_st_funds)
    public void btn_st_funds(View view){
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(amount)) {
        setPost(amount);}

    }

    @OnClick (R.id.btn_st_data)
    public void btn_st_data(View view){
        setData();
    }

    @OnClick (R.id.btn_buy)
    public void btn_buy(View view){
        String stockName = titleEt.getText().toString().trim();
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(stockName) && !TextUtils.isEmpty(amount)) {
            buyPost(stockName, amount);
        }
    }

    @OnClick (R.id.btn_can_buy)
    public void btn_can_buy(View view){
        adapter.add("BANANA");
        adapter.notifyDataSetChanged();
        String stockName = titleEt.getText().toString().trim();
        String amount = bodyEt.getText().toString().trim();
        if(!TextUtils.isEmpty(stockName) && !TextUtils.isEmpty(amount)) {
            canBuyPost(stockName, amount);
        }
    }

    public void setData() {
        Log.wtf("SET","Data");
        mAPIData.setData(new FooRequest("NOT","VALID")).enqueue(new Callback<List<SponsorsResult>>() {
            @Override
            public void onResponse(Call<List<SponsorsResult>> call, Response<List<SponsorsResult>> response) {

                if(response.isSuccessful()) {
                    List<SponsorsResult> rs = response.body();
                    showResponse(rs.toString());
                    Log.i(TAG, "post submitted to API set." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<SponsorsResult>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to AP setI.");
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
                Log.e(TAG, "Unable to submit post to AP setI.");
            }
        });
    }

    public void sendPostAvFunds() {
        Log.wtf("AV","FUNDS");
        mAPIService.avFund(new FooRequest("NOT","VALID")).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API av." + response.body().toString());
                    mTextView.setText(response.body().toString());
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

    @OnClick(R.id.requestO)
    public void requestO(View view) {
    }

    class UpdateBallTask extends TimerTask {

        public void run() {
            sendPostAvFunds();
        }
    }
}


