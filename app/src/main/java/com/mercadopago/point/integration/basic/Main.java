package com.mercadopago.point.integration.basic;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import com.mercadopago.point.integration.R;

import java.util.List;

public class Main extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static final String LAUNCHER_INTENT_DESCRIPTION = "launcher_intent_description";
    public static final String LAUNCHER_INTENT_AMOUNT = "launcher_intent_amount";
    public static final String LAUNCHER_INTENT_TYPE = "launcher_intent_type";
    public static final String LAUNCHER_INTENT_INSTALLMENTS = "launcher_intent_installments";

    public static final String LAUNCHER_APP_ID = "launcher_app_id";
    public static final String LAUNCHER_APP_SECRET = "launcher_app_secret";
    public static final String LAUNCHER_APP_FEE = "launcher_app_fee";

    public static final int PAYMENT_REQUEST = 1;

    EditText reference;
    EditText amount;
    EditText installments;
    FloatingActionButton go;

    String cc_selected;

    String appId = "MyAppID";
    String appSecret = "MySecret";
    double appFee = 12.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference = (EditText) findViewById(R.id.reference);
        amount = (EditText) findViewById(R.id.amount);

        final Spinner spinner = (Spinner) findViewById(R.id.debit_credit);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cc_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        installments = (EditText) findViewById(R.id.installments);
        go = (FloatingActionButton) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("com.mercadopago.PAYMENT_ACTION");
                // AppId
                i.putExtra(LAUNCHER_APP_ID, appId);
                // Secret
                i.putExtra(LAUNCHER_APP_SECRET, appSecret);
                // App Fee
                i.putExtra(LAUNCHER_APP_FEE, appFee);
                // Amount of transaction
                i.putExtra(LAUNCHER_INTENT_AMOUNT, amount.getText().toString());
                // Description of transaction
                i.putExtra(LAUNCHER_INTENT_DESCRIPTION, reference.getText().toString());
                if (spinner.getSelectedItemPosition() == 0) {
                    cc_selected = "credit_card";
                } else {
                    cc_selected = "debit_card";
                }
                // Payment type of transaction ( credit_card | debit_card  )
                i.putExtra(LAUNCHER_INTENT_TYPE, cc_selected);
                // # of installments
                i.putExtra(LAUNCHER_INTENT_INSTALLMENTS, installments.getText().toString());
                // Before we can start the intent, we should check if this phone handle the intent?
                if (isAvailable(i)) {
                    // start activity for result
                    startActivityForResult(i, PAYMENT_REQUEST);
                } else {
                    // send to google play.
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                }

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_REQUEST && data != null) {
            Intent intent = new Intent(this, Resultado.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
        }

    }

    public boolean isAvailable(Intent intent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
