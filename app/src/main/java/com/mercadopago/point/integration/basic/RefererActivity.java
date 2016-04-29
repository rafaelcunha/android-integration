package com.mercadopago.point.integration.basic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mercadopago.point.integration.R;

import java.util.Iterator;

public class RefererActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referer);

        tv = (TextView) findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();
        Iterator it = extras.keySet().iterator();
        StringBuffer sb = new StringBuffer();
        while(it.hasNext()) {
            String key = (String) it.next();
            sb.append(key + " : "+ extras.get(key) + "\n" );
        }

        tv.setText("REFERER " + sb.toString());

    }
}
