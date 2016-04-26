
# MP Mobile Android Seller Integration

The purpose of this app is to show the integration with  MP Mobile Android Seller.
[![Download it or see some application screenshots directly from the Google Play](http://developer.android.com/images/brand/en_generic_rgb_wo_60.png "Download it or see some application screenshots directly from the Google Play")](https://play.google.com/store/apps/details?id=com.mercadopago.merchant)

## How to:

There are 2 kinds of integration Basic and Advanced.

### Basic:


For the basic integration you have to create a [implicit intent](http://developer.android.com/intl/es/guide/components/intents-filters.html#ExampleSend) and start it with the method [startActivityForResult]('http://developer.android.com/intl/es/reference/android/app/Activity.html#startActivityForResult(android.content.Intent, int)') as follows:

```java

  
    String AMOUNT = "amount";
    String DESCRIPTION = "description";
    String CARD_TYPE = "card_type";
    String INSTALLMENTS = "installments";
    String APP_ID = "app_id";
    String APP_SECRET = "app_secret";
    String APP_FEE = "app_fee";

    ...
    ...


     Intent i = new Intent();
                    i.setAction("com.mercadopago.PAYMENT_ACTION");
                    Bundle bundle = new Bundle();
                    // AppId
                    bundle.putString(BundleCodes.APP_ID, appId);
                    // Secret
                    bundle.putString(BundleCodes.APP_SECRET, appSecret);
                    // App Fee
                    bundle.putDouble(BundleCodes.APP_FEE, appFee);
                    // Amount of transaction
                    bundle.putDouble(BundleCodes.AMOUNT, Double.valueOf(amount.getText().toString()));
                    // Description of transaction
                    bundle.putString(BundleCodes.DESCRIPTION, reference.getText().toString());
                    if (spinner.getSelectedItemPosition() == 0) {
                        cc_selected = "credit_card";
                    } else {
                        cc_selected = "debit_card";
                    }
                    // Payment type of transaction ( credit_card | debit_card  )
                    bundle.putString(BundleCodes.CARD_TYPE, cc_selected);
                    // # of installments
                    bundle.putInt(BundleCodes.INSTALLMENTS, Integer.valueOf(installments.getText().toString()));
                    // Before we can start the intent, we should check if this phone handle the intent?
                    if (isAvailable(i)) {
                        // start activity for result
                        i.putExtras(bundle);
                        startActivityForResult(i, PAYMENT_REQUEST);
                    } else {
                        // send to google play.
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                    }

```

And them when the payment is done, point will notify a intent as follows:

in the calling activity:

```java
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_REQUEST && data != null) {
            Intent intent = new Intent(this, Resultado.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
        }

    }
```

the result activity that shows the results:

```java
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
                paymentId.setText(data.getString(RESULT_PAYMENT_ID));
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
```

## Repo usage
*Do not clone or push to master branch.*

Create branch or fork from *develop*, then push or create pull requests (if you don't have access) to that branch.

**mpmobile-android_seller** uses this branching model:

http://nvie.com/posts/a-successful-git-branching-model/

## Contact

In case you need to contact us, just send an email to `mpmobile-seller@mercadolibre.com` and someone will answer you as soon as possible.
