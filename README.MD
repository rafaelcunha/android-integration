
# MP Mobile Android Seller Integration

The purpose of this app is to show the integration with  MP Mobile Android Seller.
[![Download it or see some application screenshots directly from the Google Play](http://developer.android.com/images/brand/en_generic_rgb_wo_60.png "Download it or see some application screenshots directly from the Google Play")](https://play.google.com/store/apps/details?id=com.mercadopago.merchant)

## How to:

There are 2 kinds of integration Basic and Advanced.

### Basic:


For the basic integration you have to create a [implicit intent][1] and start it with the method [startActivityForResult][2] as follows:

```java

                Intent i = new Intent();
                i.setAction("com.mercadopago.PAYMENT_ACTION");
                // Amount of transaction
                i.putExtra(LAUNCHER_INTENT_AMOUNT, amount.getText().toString());
                // Description of transaction
                i.putExtra(LAUNCHER_INTENT_DESCRIPTION, reference.getText().toString());
                if (spinner.getSelectedItemPositixon() == 0) {
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

```


[1]:http://developer.android.com/intl/es/guide/components/intents-filters.html#ExampleSend
[2]:http://developer.android.com/intl/es/reference/android/app/Activity.html#startActivityForResult(android.content.Intent, int)

### Advanced:

TODO: Document

## Repo usage
*Do not clone or push to master branch.*

Create branch or fork from *develop*, then push or create pull requests (if you don't have access) to that branch.

**mpmobile-android_seller** uses this branching model:

http://nvie.com/posts/a-successful-git-branching-model/

## Contact

In case you need to contact us, just send an email to `mpmobile-seller@mercadolibre.com` and someone will answer you as soon as possible.
