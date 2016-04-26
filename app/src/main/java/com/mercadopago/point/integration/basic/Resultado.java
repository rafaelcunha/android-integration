package com.mercadopago.point.integration.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercadopago.point.integration.R;

import java.util.Iterator;

public class Resultado extends AppCompatActivity {

    TextView textView;
    TextView paymentId;
    ImageView image;
    View layoutView;

    // it could be
    public final static String RESULT_STATUS = "result_status";
    public final static String RESULT_PAYMENT_ID = "paymentId";

    public final static String RESULT_STATUS_OK = "OK";
    public final static String RESULT_STATUS_FAILED = "FAILED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        textView = (TextView) findViewById(R.id.intent_results);
        image = (ImageView) findViewById(R.id.icon);
        layoutView = findViewById(R.id.payment_id_lo);
        paymentId = (TextView) findViewById(R.id.payment_id);

        Intent launcherIntent = getIntent();
        Bundle data = launcherIntent.getExtras();
        if (data != null) {

            String result = data.getString(RESULT_STATUS);

            if (RESULT_STATUS_OK.equals(result)) {
                if (android.os.Build.VERSION.SDK_INT >= 22) {
                    image.setImageDrawable(getDrawable(R.drawable.ok));
                } else {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.ok));
                }

                layoutView.setVisibility(View.VISIBLE);
                paymentId.setText(String.valueOf(data.getLong(RESULT_PAYMENT_ID)));
            }

            if (RESULT_STATUS_FAILED.equals(result)) {
                if (android.os.Build.VERSION.SDK_INT >= 22) {
                    image.setImageDrawable(getDrawable(R.drawable.fail));
                } else {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.fail));
                }
                layoutView.setVisibility(View.GONE);
            }

            Iterator keys = data.keySet().iterator();
            StringBuffer stringBuffer = new StringBuffer();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                stringBuffer.append(key + " " + String.valueOf(data.get(key)));
            }
            textView.setText(stringBuffer.toString());
        } else

        {
            // won't happen.
        }


    }
}
