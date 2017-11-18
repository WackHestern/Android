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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    @BindView(R.id.spinner1)
    Spinner spinner;
    @BindView(R.id.textO)
    TextView mTextView;
    private TextView mResponseTv;
    private APIService mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        ArrayList<String> list;

        list = new ArrayList<String>();
        for(String string:getResources().getStringArray(R.array.country_arrays)){
            list.add(string);
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.add(list.get(0));
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stockName = titleEt.getText().toString().trim();
                String amount = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(stockName) && !TextUtils.isEmpty(amount)) {
                    sendPost(stockName, amount);
                }
            }
        });
    }

    public void sendPost(String stockName, String amount) {
        mAPIService.savePost(stockName, amount).enqueue(new Callback<Post>() {
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

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @OnClick(R.id.requestO)
    public void requestO(View view) {
    }
}


