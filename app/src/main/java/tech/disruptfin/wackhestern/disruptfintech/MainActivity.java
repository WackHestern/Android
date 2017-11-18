package tech.disruptfin.wackhestern.disruptfintech;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner1);
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
    }

    @Override
    protected void onStart(){
        super.onStart();
      /**/
    }
}
