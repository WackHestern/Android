package tech.disruptfin.wackhestern.disruptfintech;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.button) Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @OnClick(R.id.button)
    public void buttonPress(){
        LaunchActivity.this.startActivity(new Intent(LaunchActivity.this, MainActivity.class));
    }

}