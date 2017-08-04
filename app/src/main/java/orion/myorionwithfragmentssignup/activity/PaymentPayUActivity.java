package orion.myorionwithfragmentssignup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import orion.myorionwithfragmentssignup.R;
import orion.myorionwithfragmentssignup.useful_Classes.UserDetails;

public class PaymentPayUActivity extends AppCompatActivity {

    EditText amt = null;
    Button pay = null;
    TextView all_vals;

    public static final String TAG = "PayUMoneySDK Sample";
    String PARENT_LINK="http://35.154.22.146/";

    String phone = "7619587673";
    String productName = "ORION";
    String firstName = "sulo";
    String txnId = "0nf7" + System.currentTimeMillis();
    String email="ma@gmail.com";
    String sUrl = "https://www.payumoney.com/mobileapp/payumoney/success.php";
    String fUrl = "https://www.payumoney.com/mobileapp/payumoney/failure.php";
    String udf1 = "";
    String udf2 = "";
    String udf3 = "";
    String udf4 = "";
    String udf5 = "";
    boolean isDebug = false;
    String key = "kSB3yLwp";
    String merchantId = "5380284" ;

    public static String token = "$2y$10$E.9idxO15sdEsMCPXcxIk.nEb4/6khiQNq2aBc36lkQ2YVNULyxNe"; //$2y$10$E.9idxO15sdEsMCPXcxIk.nEb4/6khiQNq2aBc36lkQ2YVNULyxNe


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pay_u);
        amt= (EditText) findViewById(R.id.getamnt);
        pay= (Button) findViewById(R.id.topay);
        all_vals= (TextView) findViewById(R.id.all_values);
      //  UserDetails usd=new UserDetails();


//HERE FIRST OF ALL WE NEED TO GET THE BUNDLE DATA AFTER THAT WE HAVE TO SERIALIZE THE OBJECT

        Bundle b2=getIntent().getBundleExtra("mine");
        UserDetails u= (UserDetails) b2.getSerializable("my_data");
        Log.d("ghfdh",u.getGender());
        String s="name:" +u.getPersn_name() +"\n" +"mail id:" +u.getPersn_email()+"\n"+
                "address:" +u.getPersonaddress() +"\n" +"city :" +u.getCity()+"\n"+
                "state:" +u.getState() +"\n" +"country:" +u.getCountry()+"\n"+
                "pincode:" +u.getPostcode() +"\n" +"gender:" +u.getGender()+"\n"+
                "mobile:" +u.getPhone() +"\n" +"dob:" +u.getDob();
        Log.d("ghfdhall",s);
        all_vals.setText(s);


    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private double getAmount() {


        Double amount = 10.0;

        if (isDouble(amt.getText().toString())) {
            amount = Double.parseDouble(amt.getText().toString());
            return amount;
        } else {
            Toast.makeText(getApplicationContext(), "Paying Default Amount ₹10", Toast.LENGTH_LONG).show();
            return amount;
        }
    }


    public void makePayment(View view) {


        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();


        builder.setAmount(getAmount())
                .setTnxId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(sUrl)
                .setfUrl(fUrl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(isDebug)
                .setKey(key)
                .setMerchantId(merchantId);

        PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();


        // Recommended
        calculateServerSideHashAndInitiatePayment(paymentParam);

    }

    private void calculateServerSideHashAndInitiatePayment(final PayUmoneySdkInitilizer.PaymentParam paymentParam) {

        // Replace your server side hash generator API URL
        //   String url = "https://test.payumoney.com/payment/op/calculateHashForTest";

        String url = PARENT_LINK + "payumoney_service?token=" + token + "&txnid=" + txnId + "&amount=" + getAmount() + "&firstname=" + firstName + "&email=" + email + "&phone=" + phone + "&productinfo=" + productName ;

        Log.i("url-api", "post url :  " + url);

        Toast.makeText(this, "Please wait... Generating hash from server ... ", Toast.LENGTH_LONG).show();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has(SdkConstants.STATUS)) {
                        String status = jsonObject.optString(SdkConstants.STATUS);
                        if (status != null || status.equals("1")) {

                            String hash = jsonObject.getString(SdkConstants.RESULT);
                            Log.i("app_activity", "Server calculated Hash :  " + hash);

                            paymentParam.setMerchantHash(hash);

                            PayUmoneySdkInitilizer.startPaymentActivityForResult(PaymentPayUActivity.this, paymentParam);
                        } else {
                            Toast.makeText(PaymentPayUActivity.this,
                                    jsonObject.getString(SdkConstants.RESULT),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(PaymentPayUActivity.this,
                            PaymentPayUActivity.this.getString(R.string.connect_to_internet),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PaymentPayUActivity.this,
                            error.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paymentParam.getParams();
            }
        };
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {

            /*if(data != null && data.hasExtra("result")){
            String responsePayUmoney = data.getStringExtra("result");
            if(SdkHelper.checkForValidString(responsePayUmoney))
                showDialogMessage(responsePayUmoney);
        } else {
            showDialogMessage("Unable to get Status of Payment");
        }*/


            if (resultCode == RESULT_OK) {
                Log.i(TAG, "Success - Payment ID : " + data.getStringExtra(SdkConstants.PAYMENT_ID));
                String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);
                showDialogMessage("Payment Success Id : " + paymentId);
            } else if (resultCode == RESULT_CANCELED) {
                Log.i(TAG, "failure");
                showDialogMessage("cancelled");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                Log.i("app_activity", "failure");

                if (data != null) {
                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                    } else {
                        showDialogMessage("failure");
                    }
                }
                //Write your code if there's no result
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                Log.i(TAG, "User returned without login");
                showDialogMessage("User returned without login");
            }
        }
    }

    private void showDialogMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TAG);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }
}
