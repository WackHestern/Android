package tech.disruptfin.wackhestern.disruptfintech;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.button) Button mButton;

    SharedPreferences mDefaultPreferences;
    @BindView(R.id.enter) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editText.setHint("Your Name");
        editText.setEnabled(true);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @OnClick(R.id.button)
    public void buttonPress(){
        LaunchActivity.this.startActivity(new Intent(LaunchActivity.this, MainActivity.class));
        mDefaultPreferences.edit().putString(getResources().getString(R.string.current_name), editText.getText().toString().trim()).apply();
    }

    @OnClick (R.id.enter)
    public void enter(){
        mButton.setEnabled(true);
    }

}