package com.jooan.classicec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jooan.latte_core.app.Latte;
import com.jooan.latte_core.net.RestClient;
import com.jooan.latte_core.net.RestClientBuiler;
import com.jooan.latte_core.net.callback.ISuccess;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tx_test = findViewById(R.id.tx_test);
        Toast.makeText(Latte.getApplication(),"context传入",Toast.LENGTH_SHORT).show();
        new RestClientBuiler()
                .url("https://www.baidu.com")
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        tx_test.setText(response);

                    }
                })
                .Builer()
        .get();
    }
}